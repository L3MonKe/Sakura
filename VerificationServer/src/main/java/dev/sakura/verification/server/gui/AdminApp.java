package dev.sakura.verification.server.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import dev.sakura.verification.server.IRCServer;

import javax.swing.*;

public final class AdminApp {
    private AdminApp() {
    }

    public static void start(IRCServer server) {
        GuiLogBuffer.install();
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new AdminFrame(server).setVisible(true));
    }
}
