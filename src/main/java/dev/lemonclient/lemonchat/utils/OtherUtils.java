package dev.lemonclient.lemonchat.utils;

import dev.lemonclient.lemonchat.client.codec.PDecoder;
import dev.lemonclient.lemonchat.client.codec.PEncoder;
import io.netty.channel.Channel;

public class OtherUtils {
    public static void setupCompression(Channel channel, int threshold, boolean validateDecompressed) {
        if (threshold >= 0) {
            if (channel.pipeline().get("decompress") instanceof PDecoder) {
                ((PDecoder) channel.pipeline().get("decompress")).setThreshold(threshold, validateDecompressed);
            } else {
                channel.pipeline().addBefore("decoder", "decompress", new PDecoder(threshold, validateDecompressed));
            }

            if (channel.pipeline().get("compress") instanceof PEncoder) {
                ((PEncoder) channel.pipeline().get("compress")).setThreshold(threshold);
            } else {
                channel.pipeline().addBefore("encoder", "compress", new PEncoder(threshold));
            }
        } else {
            if (channel.pipeline().get("decompress") instanceof PDecoder) {
                channel.pipeline().remove("decompress");
            }

            if (channel.pipeline().get("compress") instanceof PEncoder) {
                channel.pipeline().remove("compress");
            }
        }
    }
}
