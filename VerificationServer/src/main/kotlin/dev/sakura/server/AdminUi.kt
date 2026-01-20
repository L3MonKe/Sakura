package dev.sakura.server

import java.awt.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.swing.*
import javax.swing.table.DefaultTableModel

internal fun showAdminUi(store: JsonStore) {
    EventQueue.invokeLater {
        val frame = JFrame("VerificationServer 管理面板")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.add(AdminPanel(store), BorderLayout.CENTER)
        frame.minimumSize = Dimension(900, 560)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
}

private class AdminPanel(private val store: JsonStore) : JPanel(BorderLayout()) {
    private val tabs = JTabbedPane()

    private val usersModel = DefaultTableModel(arrayOf("ID", "用户名", "创建时间", "拥有卡密数"), 0)
    private val usersTable = JTable(usersModel)

    private val licensesModel =
        DefaultTableModel(arrayOf("卡密", "状态", "有效天数", "激活时间", "到期时间", "用户ID"), 0)
    private val licensesTable = JTable(licensesModel)

    private val genCountField = JTextField("10", 6)
    private val genDaysField = JTextField("30", 6)
    private val timeFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())

    init {
        val top = JPanel(BorderLayout())
        top.add(JLabel("数据目录: " + store.dir, SwingConstants.LEFT), BorderLayout.CENTER)

        val tools = JPanel(GridBagLayout())
        val gc = GridBagConstraints().apply {
            insets = Insets(4, 4, 4, 4)
            anchor = GridBagConstraints.WEST
        }
        gc.gridx = 0
        gc.gridy = 0
        tools.add(JLabel("生成数量"), gc)
        gc.gridx = 1
        tools.add(genCountField, gc)
        gc.gridx = 2
        tools.add(JLabel("有效天数"), gc)
        gc.gridx = 3
        tools.add(genDaysField, gc)
        gc.gridx = 4
        val genBtn = JButton("生成卡密")
        tools.add(genBtn, gc)
        gc.gridx = 5
        val refreshBtn = JButton("刷新")
        tools.add(refreshBtn, gc)
        gc.gridx = 6
        val banBtn = JButton("封禁选中卡密")
        tools.add(banBtn, gc)
        gc.gridx = 7
        val revokeBtn = JButton("撤销选中卡密")
        tools.add(revokeBtn, gc)
        top.add(tools, BorderLayout.EAST)

        add(top, BorderLayout.NORTH)

        tabs.addTab("用户", JScrollPane(usersTable))
        tabs.addTab("卡密", JScrollPane(licensesTable))
        add(tabs, BorderLayout.CENTER)

        fun refreshAll() {
            val selectedUserId = run {
                val row = usersTable.selectedRow
                if (row < 0) null else usersModel.getValueAt(row, 0)?.toString()
            }
            val selectedLicenseKey = run {
                val row = licensesTable.selectedRow
                if (row < 0) null else licensesModel.getValueAt(row, 0)?.toString()
            }

            store.reload()
            refreshUsers()
            refreshLicenses()

            if (!selectedUserId.isNullOrBlank()) {
                for (i in 0 until usersModel.rowCount) {
                    if (usersModel.getValueAt(i, 0)?.toString() == selectedUserId) {
                        usersTable.setRowSelectionInterval(i, i)
                        usersTable.scrollRectToVisible(usersTable.getCellRect(i, 0, true))
                        break
                    }
                }
            }
            if (!selectedLicenseKey.isNullOrBlank()) {
                for (i in 0 until licensesModel.rowCount) {
                    if (licensesModel.getValueAt(i, 0)?.toString() == selectedLicenseKey) {
                        licensesTable.setRowSelectionInterval(i, i)
                        licensesTable.scrollRectToVisible(licensesTable.getCellRect(i, 0, true))
                        break
                    }
                }
            }
        }

        genBtn.addActionListener {
            val count = genCountField.text.trim().toIntOrNull()?.coerceIn(1, 5000) ?: 10
            val days = genDaysField.text.trim().toIntOrNull()?.coerceIn(1, 3650) ?: 30
            store.generateLicenses(count, days)
            refreshAll()
        }

        refreshBtn.addActionListener { refreshAll() }

        fun selectedLicenseKey(): String? {
            val row = licensesTable.selectedRow
            if (row < 0) return null
            return licensesModel.getValueAt(row, 0)?.toString()
        }

        banBtn.addActionListener {
            val key = selectedLicenseKey() ?: return@addActionListener
            store.updateLicenseStatus(key, "BANNED")
            refreshAll()
        }

        revokeBtn.addActionListener {
            val key = selectedLicenseKey() ?: return@addActionListener
            store.updateLicenseStatus(key, "REVOKED")
            refreshAll()
        }

        val autoRefresh = Timer(1000) { refreshAll() }
        autoRefresh.isRepeats = true
        autoRefresh.start()

        SwingUtilities.invokeLater { refreshAll() }
    }

    private fun fmtSec(sec: Long?): String {
        if (sec == null) return ""
        if (sec <= 0L) return ""
        return timeFmt.format(Instant.ofEpochSecond(sec))
    }

    private fun refreshUsers() {
        usersModel.rowCount = 0
        val users = store.listUsers()
        val licenses = store.listLicenses()
        val countByUser = licenses.groupingBy { it.userId }.eachCount()
        for (u in users) {
            val cnt = countByUser[u.id] ?: 0
            usersModel.addRow(arrayOf<Any>(u.id, u.username, fmtSec(u.createdAt), cnt))
        }
    }

    private fun refreshLicenses() {
        licensesModel.rowCount = 0
        val licenses = store.listLicenses()
        for (l in licenses) {
            licensesModel.addRow(
                arrayOf<Any>(
                    l.key,
                    l.status,
                    l.validDays,
                    fmtSec(l.activatedAt),
                    fmtSec(l.expiresAt),
                    l.userId ?: ""
                )
            )
        }
    }
}
