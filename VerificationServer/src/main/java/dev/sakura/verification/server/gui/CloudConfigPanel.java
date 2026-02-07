package dev.sakura.verification.server.gui;

import dev.sakura.verification.server.IRCServer;
import dev.sakura.verification.server.service.AdminService;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class CloudConfigPanel extends JPanel {
    private final AdminService service;
    private final JTextField ownerField = new JTextField(16);
    private final JTextField nameField = new JTextField(16);
    private final JButton listBtn = new JButton("列表");
    private final JButton loadBtn = new JButton("加载");
    private final JButton saveBtn = new JButton("保存");
    private final JButton deleteBtn = new JButton("删除");
    private final JButton importBtn = new JButton("导入");
    private final JButton exportBtn = new JButton("导出");

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> nameList = new JList<>(listModel);
    private final JTextArea editor = new JTextArea();
    private volatile String currentLoadedName = "";
    private volatile String currentLoadedOwner = "";

    public CloudConfigPanel(IRCServer server) {
        super(new BorderLayout());
        this.service = new AdminService(server, server.getDatabase(), server.getUserRepository(), server.getCardRepository());
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        top.add(new JLabel("Owner:"));
        top.add(ownerField);
        top.add(new JLabel("Name:"));
        top.add(nameField);
        top.add(listBtn);
        top.add(loadBtn);
        top.add(saveBtn);
        top.add(deleteBtn);
        top.add(importBtn);
        top.add(exportBtn);
        add(top, BorderLayout.NORTH);

        editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        editor.setTabSize(4);

        JScrollPane left = new JScrollPane(nameList);
        JScrollPane right = new JScrollPane(editor);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.25);
        add(split, BorderLayout.CENTER);

        listBtn.addActionListener(e -> refreshList());
        loadBtn.addActionListener(e -> loadSelected());
        saveBtn.addActionListener(e -> saveCurrent());
        deleteBtn.addActionListener(e -> deleteCurrent());
        importBtn.addActionListener(e -> importFromFile());
        exportBtn.addActionListener(e -> exportToFile());

        nameList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String v = nameList.getSelectedValue();
                if (v != null) {
                    nameField.setText(v);
                }
            }
        });
    }

    private String owner() {
        String v = ownerField.getText();
        return v == null ? "" : v.trim();
    }

    private String name() {
        String v = nameField.getText();
        return v == null ? "" : v.trim();
    }

    private void refreshList() {
        String owner = owner();
        if (owner.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Owner 不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        listBtn.setEnabled(false);
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return service.listCloudConfigNames(owner);
            }

            @Override
            protected void done() {
                try {
                    List<String> names = get();
                    listModel.clear();
                    for (String n : names) {
                        listModel.addElement(n);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CloudConfigPanel.this, "获取列表失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                } finally {
                    listBtn.setEnabled(true);
                }
            }
        }.execute();
    }

    private void loadSelected() {
        String owner = owner();
        String name = name();
        if (owner.isEmpty() || name.isEmpty()) {
            return;
        }
        loadBtn.setEnabled(false);
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return service.getCloudConfigContent(owner, name);
            }

            @Override
            protected void done() {
                try {
                    String content = get();
                    if (content == null) {
                        JOptionPane.showMessageDialog(CloudConfigPanel.this, "配置不存在", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    currentLoadedOwner = owner;
                    currentLoadedName = name;
                    editor.setText(content);
                    editor.setCaretPosition(0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CloudConfigPanel.this, "加载失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                } finally {
                    loadBtn.setEnabled(true);
                }
            }
        }.execute();
    }

    private void saveCurrent() {
        String owner = owner();
        String name = name();
        if (owner.isEmpty() || name.isEmpty()) {
            return;
        }
        String content = editor.getText();
        saveBtn.setEnabled(false);
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return service.saveCloudConfig(owner, name, content == null ? "" : content);
            }

            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (!ok) {
                        int max = service.getCloudConfigMax(owner);
                        JOptionPane.showMessageDialog(CloudConfigPanel.this, "保存失败（可能已达到上限: " + max + "）", "提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    currentLoadedOwner = owner;
                    currentLoadedName = name;
                    refreshList();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CloudConfigPanel.this, "保存失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                } finally {
                    saveBtn.setEnabled(true);
                }
            }
        }.execute();
    }

    private void deleteCurrent() {
        String owner = owner();
        String name = name();
        if (owner.isEmpty() || name.isEmpty()) {
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "确定删除 " + owner + "/" + name + " ?", "确认", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }
        deleteBtn.setEnabled(false);
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return service.deleteCloudConfig(owner, name);
            }

            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (!ok) {
                        JOptionPane.showMessageDialog(CloudConfigPanel.this, "配置不存在", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                    if (owner.equals(currentLoadedOwner) && name.equals(currentLoadedName)) {
                        currentLoadedOwner = "";
                        currentLoadedName = "";
                        editor.setText("");
                    }
                    refreshList();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CloudConfigPanel.this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                } finally {
                    deleteBtn.setEnabled(true);
                }
            }
        }.execute();
    }

    private void importFromFile() {
        try {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            int r = chooser.showOpenDialog(this);
            if (r != javax.swing.JFileChooser.APPROVE_OPTION) {
                return;
            }
            Path p = chooser.getSelectedFile().toPath();
            editor.setText(Files.readString(p, StandardCharsets.UTF_8));
            editor.setCaretPosition(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "导入失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToFile() {
        try {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            int r = chooser.showSaveDialog(this);
            if (r != javax.swing.JFileChooser.APPROVE_OPTION) {
                return;
            }
            Path p = chooser.getSelectedFile().toPath();
            Files.writeString(p, editor.getText() == null ? "" : editor.getText(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "导出失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
