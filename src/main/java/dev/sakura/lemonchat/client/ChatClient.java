package dev.sakura.lemonchat.client;

import dev.sakura.lemonchat.client.codec.Int0Decoder;
import dev.sakura.lemonchat.client.codec.Int1Decoder;
import dev.sakura.lemonchat.network.NetworkPacketRegistry;
import dev.sakura.lemonchat.network.Packet;
import dev.sakura.lemonchat.network.c2s.ChatMessageC2S;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatClient {
    public static final Logger LOGGER = LogManager.getLogger("LemonChat-Client");
    public String address;
    public int port;

    public final boolean running = true;

    public Bootstrap bootstrap;
    public ClientSession session;
    private final EventLoopGroup group;
    private static ChatClient INSTANCE;

    public static ChatClient get() {
        if (ChatClient.INSTANCE == null) {
            ChatClient.main(null);
        }

        return ChatClient.INSTANCE;
    }

    public ChatClient(String address, int port) {
        this.address = address;
        this.port = port;
        INSTANCE = this;

        new Thread(this::update, "ClientTicker").start();

        group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel channel) {
                try {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                } catch (ChannelException ignored) {
                }

                channel.pipeline()
                        .addLast("timeout", new ReadTimeoutHandler(30))
                        .addLast("splitter", new Int0Decoder())
                        .addLast("decoder", new SPacketDecoder())
                        .addLast("prepender", new Int1Decoder())
                        .addLast("encoder", new SPacketEncoder())
                        .addLast("packet_handler", session = new ClientSession(ChatClient.this));
            }
        });
    }

    public void connect() throws InterruptedException {
        new Thread(() -> {
            try {
                LOGGER.info("[LemonChat] Connecting...");
                if (this.session != null && session.isConnected()) {
                    this.session.disconnect("Disconnect.");
                }

                this.bootstrap.connect(address, port).sync();
            } catch (Exception e) {
            }
        }, "ChatNetty").start();
    }

    public void update() {
        while (true) {
            tick();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void chat(String message) {
        sendPacket(new ChatMessageC2S(message));
    }

    private void tick() {
        if (this.session != null) {
            this.session.tick();
        }
    }

    public void sendPacket(Packet packet) {
        if (this.session != null) {
            this.session.sendPacket(packet);
        }
    }

    public static void main(String[] args) {
        if (NetworkPacketRegistry.INSTANCE == null) {
            new NetworkPacketRegistry().load();
        }
        try {
            ChatClient client = new ChatClient("116.204.133.141", 14778);
            client.connect();
        } catch (Exception ignored) {
        }
    }
}
