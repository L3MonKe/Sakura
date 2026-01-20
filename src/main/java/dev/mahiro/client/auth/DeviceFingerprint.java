package dev.mahiro.client.auth;

import oshi.SystemInfo;
import oshi.hardware.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public final class DeviceFingerprint {
    private DeviceFingerprint() {
    }

    public static String computeDeviceId() {
        try {
            HardwareAbstractionLayer hal = new SystemInfo().getHardware();

            ComputerSystem cs = hal.getComputerSystem();
            String baseboardSerial = safe(cs.getBaseboard().getSerialNumber());
            String systemSerial = safe(cs.getSerialNumber());
            String hardwareUuid = safe(cs.getHardwareUUID());
            String manufacturer = safe(cs.getManufacturer());
            String model = safe(cs.getModel());

            CentralProcessor cpu = hal.getProcessor();
            String cpuId = safe(cpu.getProcessorIdentifier().getProcessorID());
            String cpuName = safe(cpu.getProcessorIdentifier().getName());
            String cpuVendor = safe(cpu.getProcessorIdentifier().getVendor());

            List<GraphicsCard> gpus = hal.getGraphicsCards();
            String gpuNames = gpus.stream().map(GraphicsCard::getName).map(DeviceFingerprint::safe).sorted().collect(Collectors.joining("|"));

            List<HWDiskStore> disks = hal.getDiskStores();
            String diskSerials = disks.stream()
                    .map(d -> safe(d.getSerial()))
                    .filter(s -> !s.isBlank())
                    .sorted()
                    .collect(Collectors.joining("|"));

            List<NetworkIF> nics = hal.getNetworkIFs();
            String macs = nics.stream()
                    .sorted(Comparator.comparing(NetworkIF::getName, Comparator.nullsLast(String::compareToIgnoreCase)))
                    .map(NetworkIF::getMacaddr)
                    .map(DeviceFingerprint::safe)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.joining("|"));

            String os = safe(System.getProperty("os.name"));
            String osVer = safe(System.getProperty("os.version"));
            String arch = safe(System.getProperty("os.arch"));

            String raw = String.join(";",
                    "bb=" + baseboardSerial,
                    "ss=" + systemSerial,
                    "uuid=" + hardwareUuid,
                    "m=" + manufacturer,
                    "model=" + model,
                    "cpu=" + cpuId,
                    "cpuName=" + cpuName,
                    "cpuVendor=" + cpuVendor,
                    "gpu=" + gpuNames,
                    "disk=" + diskSerials,
                    "mac=" + macs,
                    "os=" + os,
                    "osv=" + osVer,
                    "arch=" + arch
            );

            return sha256Hex(normalize(raw));
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private static String normalize(String s) {
        String v = Objects.requireNonNullElse(s, "");
        v = v.replace('\u0000', ' ');
        v = v.replaceAll("\\s+", " ").trim();
        return v.toLowerCase(Locale.ROOT);
    }

    private static String safe(String s) {
        return Objects.requireNonNullElse(s, "").trim();
    }

    private static String sha256Hex(String s) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

