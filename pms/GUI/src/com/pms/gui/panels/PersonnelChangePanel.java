package com.pms.gui.panels;

import com.pms.gui.dialogs.PersonnelChangeDialog;
import com.pms.model.Employee;
import com.pms.model.PersonnelChange;
import com.pms.utils.DBConnection;
import com.pms.utils.SwingUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonnelChangePanel extends JPanel {
    private JTable changeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public PersonnelChangePanel() {
        initUI();
        loadChangeData();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 顶部工具栏
        JPanel toolPanel = new JPanel(new BorderLayout());

        // 搜索框
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
        JButton refreshBtn = new JButton("刷新");

        addBtn.addActionListener(this::addChangeRecord);
        refreshBtn.addActionListener(e -> loadChangeData());

        btnPanel.add(addBtn);
        btnPanel.add(refreshBtn);
        toolPanel.add(btnPanel, BorderLayout.EAST);

        add(toolPanel, BorderLayout.NORTH);

        // 创建表格
        String[] columnNames = {"记录ID", "员工ID", "员工姓名", "变动类型", "变动描述", "变动时间"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        changeTable = new JTable(tableModel);
        // 设置列宽
        changeTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        changeTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        changeTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        changeTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        changeTable.getColumnModel().getColumn(4).setPreferredWidth(250);
        changeTable.getColumnModel().getColumn(5).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(changeTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    // 加载变动记录数据
    private void loadChangeData() {
        tableModel.setRowCount(0);
        List<PersonnelChange> changes = getSampleChanges();

        for (PersonnelChange change : changes) {
            Object[] rowData = {
                    change.getId(),
                    change.getEmployeeId(),
                    change.getEmployeeName(),
                    change.getChangeType(),
                    change.getDescription()
            };
            tableModel.addRow(rowData);
        }
    }

    // 模拟变动记录数据
    private List<PersonnelChange> getSampleChanges() {
        List<PersonnelChange> changes = new ArrayList<>();
        String sql = "SELECT id, person, name, 'change', description FROM personnel";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // 提取字段值（同样需与数据库表列名一致）
                int id = rs.getInt("id");
                int person = rs.getInt("person");
                String name = rs.getString("name");
                String change = rs.getString("change");
                String description = rs.getString("description");

                // 3. 批量添加到列表（无需逐个写 add 语句）
                changes.add(new PersonnelChange(id, person, name, change, description));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return changes;
    }

    // 搜索变动记录
    private void searchChanges(ActionEvent e) {
        String keyword = searchField.getText().trim();
        JOptionPane.showMessageDialog(this, "搜索员工变动记录: " + keyword);
    }

    // 添加变动记录
    private void addChangeRecord(ActionEvent e) {
        PersonnelChangeDialog dialog = new PersonnelChangeDialog(SwingUtil.getParentFrame(this), "添加人事变动记录");
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            loadChangeData();
        }
    }
}