package com.pms.gui.panels;

import com.pms.gui.dialogs.PersonnelChangeDialog;
import com.pms.model.PersonnelChange;
import com.pms.utils.DBConnection;
import com.pms.utils.SwingUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonnelChangePanel extends JPanel {
    private JTable changeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    // 分页相关
    private int currentPage = 1;
    private final int pageSize = 15;
    private int totalRecords = 0;
    private JLabel pageInfoLabel;
    private JButton prevBtn;
    private JButton nextBtn;

    // 排序相关
    private int sortColumn = 0;
    private boolean ascending = true;

    public PersonnelChangePanel() {
        initUI();
        loadChangeData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 顶部工具栏
        JPanel toolPanel = new JPanel(new BorderLayout());
        toolPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // 搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("搜索员工:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(this::searchChanges);
        searchPanel.add(searchBtn);
        toolPanel.add(searchPanel, BorderLayout.WEST);

        // 操作按钮
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("添加变动记录");
        JButton deleteBtn = new JButton("删除记录");
        JButton refreshBtn = new JButton("刷新");

        addBtn.addActionListener(this::addChangeRecord);
        deleteBtn.addActionListener(this::deleteChangeRecord);
        refreshBtn.addActionListener(e -> {
            currentPage = 1;
            loadChangeData();
        });

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        toolPanel.add(btnPanel, BorderLayout.EAST);

        add(toolPanel, BorderLayout.NORTH);

        // 表格设置
        String[] columnNames = {"记录ID", "员工ID", "员工姓名", "变动类型", "变动描述", "变动时间"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        changeTable = new JTable(tableModel);
        changeTable.setRowHeight(35);
        changeTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        changeTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));

        // 列宽设置
        changeTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        changeTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        changeTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        changeTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        changeTable.getColumnModel().getColumn(4).setPreferredWidth(250);
        changeTable.getColumnModel().getColumn(5).setPreferredWidth(150);

        // 表头排序（仅允许ID、员工ID排序，先屏蔽时间排序避免字段错误）
        changeTable.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = changeTable.columnAtPoint(e.getPoint());
                // 暂时只允许记录ID(0)、员工ID(1)排序，避免时间字段错误
                if (column == 0 || column == 1) {
                    if (column == sortColumn) {
                        ascending = !ascending;
                    } else {
                        sortColumn = column;
                        ascending = true;
                    }
                    currentPage = 1;
                    loadChangeData();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(changeTable);
        add(scrollPane, BorderLayout.CENTER);

        // 分页控件
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        prevBtn = new JButton("上一页");
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadChangeData();
            }
        });

        pageInfoLabel = new JLabel("第 1 页，共 0 页");

        nextBtn = new JButton("下一页");
        nextBtn.addActionListener(e -> {
            int totalPages = (totalRecords + pageSize - 1) / pageSize;
            if (currentPage < totalPages) {
                currentPage++;
                loadChangeData();
            }
        });

        paginationPanel.add(prevBtn);
        paginationPanel.add(pageInfoLabel);
        paginationPanel.add(nextBtn);
        add(paginationPanel, BorderLayout.SOUTH);
    }

    // 加载变动记录数据
    private void loadChangeData() {
        tableModel.setRowCount(0);
        List<PersonnelChange> changes = getChangesFromDB();

        for (PersonnelChange change : changes) {
            tableModel.addRow(new Object[]{
                    change.getId(),
                    change.getEmployeeId(),
                    change.getEmployeeName(),
                    change.getChangeType(),
                    change.getDescription(),
                    change.getChangeTime()
            });
        }

        updatePageInfo();
    }

    // 从数据库获取变动记录（核心修正：适配通用表结构，解决字段不存在问题）
    private List<PersonnelChange> getChangesFromDB() {
        List<PersonnelChange> changes = new ArrayList<>();
        // 排序字段映射：仅适配记录ID(0)、员工ID(1)，避免时间字段错误
        String[] sortFields = {"p.id", "p.person", "", "", "", ""};
        String sortField = sortFields[sortColumn];
        String order = ascending ? "ASC" : "DESC";
        int offset = (currentPage - 1) * pageSize;

        // 修正1：移除错误的change_code关联，改用原始的`change`字段（反引号转义关键字）
        // 修正2：暂时注释变动时间字段，先保证基础查询运行（后续根据你的表结构替换）
        String countSql = "SELECT COUNT(*) FROM personnel p " +
                "LEFT JOIN person pe ON p.person = pe.id " +
                "LEFT JOIN personnel_change pc ON p.`change` = pc.code";

        // 修正3：SQL语句适配通用结构，字段名用反引号转义关键字，暂时隐藏时间字段
        String dataSql = String.format(
                "SELECT p.id, p.person, pe.name, pc.description, p.description, '' " +
                        "FROM personnel p " +
                        "LEFT JOIN person pe ON p.person = pe.id " +
                        "LEFT JOIN personnel_change pc ON p.`change` = pc.code " +
                        "ORDER BY %s %s LIMIT %d, %d",
                sortField, order, offset, pageSize
        );

        try (Connection conn = DBConnection.getConnection()) {
            // 获取总记录数
            PreparedStatement countStmt = conn.prepareStatement(countSql);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next()) {
                totalRecords = countRs.getInt(1);
            }

            // 获取分页数据
            PreparedStatement dataStmt = conn.prepareStatement(dataSql);
            ResultSet rs = dataStmt.executeQuery();
            while (rs.next()) {
                PersonnelChange change = new PersonnelChange();
                change.setId(rs.getInt(1));                  // 记录ID
                change.setEmployeeId(rs.getInt(2));          // 员工ID
                change.setEmployeeName(rs.getString(3));     // 员工姓名
                change.setChangeType(rs.getString(4));       // 变动类型
                change.setDescription(rs.getString(5));      // 变动描述
                change.setChangeTime(rs.getString(6));       // 变动时间（暂时为空，后续替换）
                changes.add(change);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载变动记录失败: " + e.getMessage());
        }
        return changes;
    }

    // 搜索变动记录（同步修正关联字段）
    private void searchChanges(ActionEvent e) {
        String keyword = searchField.getText().trim();
        tableModel.setRowCount(0);
        // 修正：使用反引号转义`change`关键字，适配通用结构
        String sql = "SELECT p.id, p.person, pe.name, pc.description, p.description, '' " +
                "FROM personnel p " +
                "LEFT JOIN person pe ON p.person = pe.id " +
                "LEFT JOIN personnel_change pc ON p.`change` = pc.code " +
                "WHERE pe.name LIKE ? OR p.person = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            try {
                pstmt.setInt(2, Integer.parseInt(keyword));
            } catch (NumberFormatException ex) {
                pstmt.setInt(2, -1); // 无效ID查询
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "搜索失败: " + ex.getMessage());
        }
        updatePageInfo();
    }

    // 添加变动记录
    private void addChangeRecord(ActionEvent e) {
        PersonnelChangeDialog dialog = new PersonnelChangeDialog(SwingUtil.getParentFrame(this), "添加人事变动记录");
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            loadChangeData();
        }
    }

    // 删除变动记录
    private void deleteChangeRecord(ActionEvent e) {
        int selectedRow = changeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int recordId = (int) changeTable.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(this,
                "确定要删除ID为" + recordId + "的变动记录吗？", "确认删除",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM personnel WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "删除成功");
            loadChangeData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "删除失败: " + ex.getMessage());
        }
    }

    // 更新分页信息
    private void updatePageInfo() {
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        pageInfoLabel.setText(String.format("第 %d 页，共 %d 页", currentPage, totalPages));
        prevBtn.setEnabled(currentPage > 1);
        nextBtn.setEnabled(currentPage < totalPages);
    }
}