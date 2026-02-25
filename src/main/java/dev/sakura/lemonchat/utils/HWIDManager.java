package dev.sakura.lemonchat.utils;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HWIDManager {
    public static String getHwid() {
        try {
            CentralProcessor processor = new SystemInfo().getHardware().getProcessor();
            return hash(
                    processor.getProcessorIdentifier().getProcessorID()
                            + processor.getProcessorIdentifier().getName()
                            + processor.getProcessorIdentifier().getVendor()
                            + System.getProperty("os.arch")
                            + System.getProperty("os.name")
                            + System.getProperty("os.version")
            );
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private static String hash(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
