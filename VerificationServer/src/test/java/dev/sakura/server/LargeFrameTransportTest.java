package dev.sakura.server;

import dev.sakura.server.packet.IRCPacket;
import dev.sakura.server.packet.implemention.c2s.CloudConfigC2S;
import dev.sakura.server.processor.IRCProtocol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.transport.AioQuickServer;
import org.smartboot.socket.transport.AioSession;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class LargeFrameTransportTest {
    @Test
    public void serverAcceptsLargeFrame() throws Exception {
        int port;
        try (ServerSocket s = new ServerSocket(0)) {
            port = s.getLocalPort();
        }

        IRCProtocol protocol = new IRCProtocol();
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<IRCPacket> received = new AtomicReference<>();

        MessageProcessor<IRCPacket> processor = new MessageProcessor<>() {
            @Override
            public void process(AioSession session, IRCPacket packet) {
                received.set(packet);
                latch.countDown();
            }
        };

        AioQuickServer server = new AioQuickServer(port, protocol, processor);
        try {
            Method m = server.getClass().getMethod("setReadBufferSize", int.class);
            m.invoke(server, 8 * 1024 * 1024 + Integer.BYTES);
        } catch (Exception ignored) {
        }
        server.setBannerEnabled(false);
        server.start();

        try (Socket socket = new Socket("127.0.0.1", port)) {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 1024 * 64));
            String content = "x".repeat(512 * 1024);
            byte[] payload = protocol.encode(new CloudConfigC2S("upload", "", "Fin", content));
            out.writeInt(payload.length);
            out.write(payload);
            out.flush();
            Assertions.assertTrue(latch.await(3, TimeUnit.SECONDS), "impl did not receive packet");
            Assertions.assertInstanceOf(CloudConfigC2S.class, received.get());
        } finally {
            tryInvoke(server, "shutdown");
            tryInvoke(server, "stop");
            tryInvoke(server, "close");
        }
    }

    private static void tryInvoke(Object target, String methodName) {
        if (target == null) {
            return;
        }
        try {
            Method m = target.getClass().getMethod(methodName);
            m.invoke(target);
        } catch (Exception ignored) {
        }
    }
}

