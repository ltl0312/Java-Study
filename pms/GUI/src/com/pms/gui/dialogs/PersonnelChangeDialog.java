package com.pms.gui.dialogs;

import com.pms.model.PersonnelChange;
import com.pms.model.Department;
import com.pms.model.Employee;
import com.pms.model.CodeNameItem;
import com.pms.service.DepartmentService;
import com.pms.service.EmployeeService;
import com.pms.service.PersonnelChangeService;
import com.pms.utils.SwingUtil;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonnelChangeDialog extends JDialog {
    private boolean confirmed = false;
    private PersonnelChange change;

    private JTextField idField;
    private JTextField employeeIdField;
    private JTextField employeeNameField;
    private JComboBox<String> changeTypeCombo;
    private JTextArea descriptionArea;
    private JTextField timeField;
    private JComboBox<CodeNameItem> oldDepartmentCombo;
    private JComboBox<CodeNameItem> newDepartmentCombo;
    private JComboBox<CodeNameItem> oldJobCombo;
    private JComboBox<CodeNameItem> newJobCombo;

    public PersonnelChangeDialog(Frame parent, String title) {
        super(parent, title, true);
        this.change = new PersonnelChange();
        initUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 员工ID
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("员工ID:"), gbc);

        gbc.gridx = 1;
        employeeIdField = new JTextField(15);
        formPanel.add(employeeIdField, gbc);

        // 员工姓名
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("员工姓名:"), gbc);

        gbc.gridx = 1;
        employeeNameField = new JTextField(15);
        formPanel.add(employeeNameField, gbc);

        // 变动类型
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("变动类型:"), gbc);

        gbc.gridx = 1;
        changeTypeCombo = new JComboBox<>(new String[]{"新员工加入", "职务变动", "部门变动", "辞退"});
        formPanel.add(changeTypeCombo, gbc);

        // 原部门
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel oldDeptLabel = new JLabel("原部门:");
        formPanel.add(oldDeptLabel, gbc);

        gbc.gridx = 1;
        oldDepartmentCombo = new JComboBox<>();
        formPanel.add(oldDepartmentCombo, gbc);

        // 新部门
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel newDeptLabel = new JLabel("新部门:");
        formPanel.add(newDeptLabel, gbc);

        gbc.gridx = 1;
        newDepartmentCombo = new JComboBox<>();
        formPanel.add(newDepartmentCombo, gbc);

        // 原职位
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel oldJobLabel = new JLabel("原职位:");
        formPanel.add(oldJobLabel, gbc);

        gbc.gridx = 1;
        oldJobCombo = new JComboBox<>();
        formPanel.add(oldJobCombo, gbc);

        // 新职位
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel newJobLabel = new JLabel("新职位:");
        formPanel.add(newJobLabel, gbc);

        gbc.gridx = 1;
        newJobCombo = new JComboBox<>();
        formPanel.add(newJobCombo, gbc);

        // 变动时间
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("变动时间:"), gbc);

        gbc.gridx = 1;
        timeField = new JTextField(15);
        timeField.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        formPanel.add(timeField, gbc);

        // 变动描述
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("变动描述:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        formPanel.add(new JScrollPane(descriptionArea), gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");

        okBtn.addActionListener(this::confirmAction);
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 初始化部门和职位下拉框
        initDepartmentCombos();
        initJobCombos();

        // 员工ID输入监听
        employeeIdField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleEmployeeIdChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleEmployeeIdChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleEmployeeIdChange();
            }
        });

        // 变动类型选择监听
        changeTypeCombo.addActionListener(e -> {
            updateFieldVisibility();
            autoSelectCurrentPosition();
        });

        // 初始隐藏字段
        updateFieldVisibility();
    }

    // 处理员工ID变化，自动获取员工信息
    private void handleEmployeeIdChange() {
        String employeeIdStr = employeeIdField.getText().trim();
        if (employeeIdStr.isEmpty()) {
            employeeNameField.setText("");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            EmployeeService employeeService = new EmployeeService();
            Employee employee = employeeService.getEmployeeById(employeeId);

            if (employee != null) {
                employeeNameField.setText(employee.getName());
                // 如果员工存在且当前已选择了变动类型，自动显示当前职位/部门
                autoSelectCurrentPosition();
            }
        } catch (NumberFormatException ex) {
            // 员工ID不是数字，不处理
        }
    }

    // 自动选择当前职位或部门
    private void autoSelectCurrentPosition() {
        String employeeIdStr = employeeIdField.getText().trim();
        if (employeeIdStr.isEmpty()) {
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            EmployeeService employeeService = new EmployeeService();
            Employee employee = employeeService.getEmployeeById(employeeId);

            if (employee != null) {
                String changeType = (String) changeTypeCombo.getSelectedItem();
                if (changeType != null) {
                    if ("职务变动".equals(changeType)) {
                        // 自动选择当前职位
                        for (int i = 0; i < oldJobCombo.getItemCount(); i++) {
                            CodeNameItem item = (CodeNameItem) oldJobCombo.getItemAt(i);
                            if (item.getCode() == employee.getJobCode()) {
                                oldJobCombo.setSelectedIndex(i);
                                break;
                            }
                        }
                    } else if ("部门变动".equals(changeType)) {
                        // 自动选择当前部门
                        for (int i = 0; i < oldDepartmentCombo.getItemCount(); i++) {
                            CodeNameItem item = (CodeNameItem) oldDepartmentCombo.getItemAt(i);
                            if (item.getCode() == employee.getDepartmentId()) {
                                oldDepartmentCombo.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException ex) {
            // 员工ID不是数字，不处理
        }
    }

    // 初始化部门下拉框
    private void initDepartmentCombos() {
        // 清空下拉框
        oldDepartmentCombo.removeAllItems();
        newDepartmentCombo.removeAllItems();

        // 添加默认选项
        oldDepartmentCombo.addItem(new CodeNameItem(-1, "-- 请选择 --"));
        newDepartmentCombo.addItem(new CodeNameItem(-1, "-- 请选择 --"));

        // 从数据库获取所有部门
        DepartmentService departmentService = new DepartmentService();
        List<Department> departments = departmentService.getAllDepartments();

        // 填充下拉框
        for (Department dept : departments) {
            CodeNameItem item = new CodeNameItem(dept.getId(), dept.getName());
            oldDepartmentCombo.addItem(item);
            newDepartmentCombo.addItem(item);
        }
    }

    // 初始化职位下拉框
    private void initJobCombos() {
        // 清空下拉框
        oldJobCombo.removeAllItems();
        newJobCombo.removeAllItems();

        // 添加默认选项
        oldJobCombo.addItem(new CodeNameItem(-1, "-- 请选择 --"));
        newJobCombo.addItem(new CodeNameItem(-1, "-- 请选择 --"));

        // 职位代码映射（从Employee类的getJobName方法获取）
        int[] jobCodes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        String[] jobNames = {"部门经理", "高级工程师", "人事专员", "会计", "销售代表", "行政助理", "初级工程师", "财务主管", "测试工程师", "算法工程师", "市场专员", "销售经理", "后勤主管", "AI 训练师", "数据架构师"};

        // 填充下拉框
        for (int i = 0; i < jobCodes.length; i++) {
            CodeNameItem item = new CodeNameItem(jobCodes[i], jobNames[i]);
            oldJobCombo.addItem(item);
            newJobCombo.addItem(item);
        }
    }

    // 根据变动类型更新字段可见性
    private void updateFieldVisibility() {
        String changeType = (String) changeTypeCombo.getSelectedItem();
        boolean showDepartmentFields = false;
        boolean showJobFields = false;
        boolean showOldDepartmentFields = false; // 是否显示原部门字段
        boolean showOldJobFields = false; // 是否显示原职位字段
        boolean showEmployeeIdFields = true; // 是否显示员工ID字段

        switch (changeType) {
            case "职务变动":
                showJobFields = true;
                showOldJobFields = true;
                break;
            case "部门变动":
                showDepartmentFields = true;
                showOldDepartmentFields = true;
                break;
            case "新员工加入":
                showDepartmentFields = true;
                showJobFields = true;
                // 新员工不需要显示原部门和原职位
                showOldDepartmentFields = false;
                showOldJobFields = false;
                // 新员工不需要输入员工ID，自动生成
                showEmployeeIdFields = false;
                break;
        }

        // 更新控件可见性
        oldDepartmentCombo.setVisible(showOldDepartmentFields);
        newDepartmentCombo.setVisible(showDepartmentFields);
        oldJobCombo.setVisible(showOldJobFields);
        newJobCombo.setVisible(showJobFields);

        // 更新员工ID字段可见性
        employeeIdField.setVisible(showEmployeeIdFields);

        // 更新标签可见性
        Component[] components = ((JPanel)((JPanel)getContentPane().getComponent(0)).getComponent(0)).getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                String text = label.getText();
                if ("原部门:".equals(text)) {
                    label.setVisible(showOldDepartmentFields);
                } else if ("新部门:".equals(text)) {
                    label.setVisible(showDepartmentFields);
                } else if ("原职位:".equals(text)) {
                    label.setVisible(showOldJobFields);
                } else if ("新职位:".equals(text)) {
                    label.setVisible(showJobFields);
                } else if ("员工ID:".equals(text)) {
                    label.setVisible(showEmployeeIdFields);
                }
            }
        }

        // 调整对话框大小
        pack();
        setLocationRelativeTo(getParent());
    }

    private void confirmAction(ActionEvent e) {
        // 数据验证
        String changeType = (String) changeTypeCombo.getSelectedItem();
        int employeeId = 0;
        
        // 对于新员工加入，自动生成员工ID
        if ("新员工加入".equals(changeType)) {
            // 获取部门ID
            if (newDepartmentCombo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "请选择部门", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 从新部门下拉框中获取部门ID
            Object selectedDeptItem = newDepartmentCombo.getSelectedItem();
            if (!(selectedDeptItem instanceof CodeNameItem)) {
                JOptionPane.showMessageDialog(this, "部门选择错误", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int departmentId = ((CodeNameItem) selectedDeptItem).getCode();
            
            // 自动生成员工ID
            EmployeeService employeeService = new EmployeeService();
            employeeId = employeeService.generateEmployeeIdByDepartmentId(departmentId);
            
            // 将生成的员工ID显示在员工ID字段中
            employeeIdField.setText(String.valueOf(employeeId));
        } else {
            // 其他类型需要验证员工ID
            if (employeeIdField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "员工ID不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                employeeId = Integer.parseInt(employeeIdField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "员工ID必须是数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (timeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "变动时间不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 验证变动类型
        if (changeType == null || changeType.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请选择变动类型", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 根据变动类型验证相关字段
        int oldDepartmentId = -1;
        int newDepartmentId = -1;
        int oldJobCode = -1;
        int newJobCode = -1;

        if ("职务变动".equals(changeType)) {
            // 验证职位选择
            CodeNameItem oldJobItem = (CodeNameItem) oldJobCombo.getSelectedItem();
            CodeNameItem newJobItem = (CodeNameItem) newJobCombo.getSelectedItem();
            if (oldJobItem.getCode() == -1) {
                JOptionPane.showMessageDialog(this, "请选择原职位", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (newJobItem.getCode() == -1) {
                JOptionPane.showMessageDialog(this, "请选择新职位", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            oldJobCode = oldJobItem.getCode();
            newJobCode = newJobItem.getCode();
        } else if ("部门变动".equals(changeType)) {
            // 验证部门选择
            CodeNameItem oldDeptItem = (CodeNameItem) oldDepartmentCombo.getSelectedItem();
            CodeNameItem newDeptItem = (CodeNameItem) newDepartmentCombo.getSelectedItem();
            if (oldDeptItem.getCode() == -1) {
                JOptionPane.showMessageDialog(this, "请选择原部门", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (newDeptItem.getCode() == -1) {
                JOptionPane.showMessageDialog(this, "请选择新部门", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            oldDepartmentId = oldDeptItem.getCode();
            newDepartmentId = newDeptItem.getCode();
        } else if ("新员工加入".equals(changeType)) {
            // 验证部门和职位选择
            CodeNameItem newDeptItem = (CodeNameItem) newDepartmentCombo.getSelectedItem();
            CodeNameItem newJobItem = (CodeNameItem) newJobCombo.getSelectedItem();
            if (newDeptItem.getCode() == -1) {
                JOptionPane.showMessageDialog(this, "请选择部门", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (newJobItem.getCode() == -1) {
                JOptionPane.showMessageDialog(this, "请选择职位", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            newDepartmentId = newDeptItem.getCode();
            newJobCode = newJobItem.getCode();
        }

        // 保存数据
        change.setEmployeeId(employeeId);
        
        // 校验员工ID和姓名是否对应（新员工加入时不需要校验）
        String inputEmployeeName = employeeNameField.getText().trim();
        if (!inputEmployeeName.isEmpty() && !"新员工加入".equals(changeType)) {
            // 使用EmployeeService获取员工信息
            com.pms.service.EmployeeService employeeService = new com.pms.service.EmployeeService();
            com.pms.model.Employee employee = employeeService.getEmployeeById(change.getEmployeeId());
            
            if (employee != null && !employee.getName().equals(inputEmployeeName)) {
                JOptionPane.showMessageDialog(this, "员工ID和姓名不匹配", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (employee == null) {
                // 如果是新员工，不需要校验姓名
                // 新员工的情况会在PersonnelChangePanel的addChangeRecord方法中处理
                // 这里只是提醒用户注意
                int option = JOptionPane.showConfirmDialog(this, "未找到该员工信息，是否继续添加？", "提示", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.NO_OPTION) {
                    return;
                }
            }
        }

        // 处理时间格式（支持yyyy-MM-dd HH:mm）
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date utilDate = sdf.parse(timeField.getText().trim());
            change.setChangeTime(new java.sql.Timestamp(utilDate.getTime()));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "时间格式错误（正确格式：yyyy-MM-dd HH:mm）", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 设置其他字段
        change.setEmployeeName(employeeNameField.getText().trim());
        change.setChangeType(changeType);
        
        // 自动生成详细的变动描述
        String description = "";
        switch (changeType) {
            case "新员工加入":
                // 获取部门名称和职位名称
                String newDeptName = ((CodeNameItem) newDepartmentCombo.getSelectedItem()).getName();
                String newJobName = ((CodeNameItem) newJobCombo.getSelectedItem()).getName();
                description = "新员工入职，部门：【" + newDeptName + "】，职位：【" + newJobName + "】";
                break;
            case "职务变动":
                // 获取原职位名称和新职位名称
                String oldJobName = ((CodeNameItem) oldJobCombo.getSelectedItem()).getName();
                newJobName = ((CodeNameItem) newJobCombo.getSelectedItem()).getName();
                
                // 检查是否同时发生部门变动
                String oldDeptName = "";
                String deptName = "";
                boolean hasDepartmentChange = false;
                
                if (oldDepartmentCombo.getSelectedItem() != null && newDepartmentCombo.getSelectedItem() != null) {
                    oldDeptName = ((CodeNameItem) oldDepartmentCombo.getSelectedItem()).getName();
                    deptName = ((CodeNameItem) newDepartmentCombo.getSelectedItem()).getName();
                    hasDepartmentChange = !oldDeptName.equals(deptName);
                }
                
                if (hasDepartmentChange) {
                    // 同时发生部门和职务变动
                    description = "部门从【" + oldDeptName + "】调整为【" + deptName + "】，职位从【" + oldJobName + "】调整为【" + newJobName + "】";
                } else {
                    // 仅职务变动
                    description = "职位从【" + oldJobName + "】调整为【" + newJobName + "】";
                }
                break;
            case "部门变动":
                // 获取原部门名称和新部门名称
                oldDeptName = ((CodeNameItem) oldDepartmentCombo.getSelectedItem()).getName();
                newDeptName = ((CodeNameItem) newDepartmentCombo.getSelectedItem()).getName();
                
                // 检查是否同时发生职务变动
                boolean hasJobChange = false;
                String jobOldName = "";
                String jobNewName = "";
                
                if (oldJobCombo.getSelectedItem() != null && newJobCombo.getSelectedItem() != null) {
                    jobOldName = ((CodeNameItem) oldJobCombo.getSelectedItem()).getName();
                    jobNewName = ((CodeNameItem) newJobCombo.getSelectedItem()).getName();
                    hasJobChange = !jobOldName.equals(jobNewName);
                }
                
                if (hasJobChange) {
                    // 同时发生部门和职务变动
                    description = "部门从【" + oldDeptName + "】调整为【" + newDeptName + "】，职位从【" + jobOldName + "】调整为【" + jobNewName + "】";
                } else {
                    // 仅部门变动
                    description = "部门从【" + oldDeptName + "】调整为【" + newDeptName + "】";
                }
                break;
            case "辞退":
                // 保持原有的描述
                description = "员工被辞退";
                break;
        }
        change.setDescription(description);
        
        // 设置部门和职位信息
        change.setOldDepartmentId(oldDepartmentId);
        change.setNewDepartmentId(newDepartmentId);
        change.setOldJobCode(oldJobCode);
        change.setNewJobCode(newJobCode);

        // 根据变动类型执行相应的员工操作
        EmployeeService employeeService = new EmployeeService();
        boolean success = false;
        String message = "";

        try {
            switch (changeType) {
                case "新员工加入":
                    // 创建新员工对象并保存
                    Employee newEmployee = new Employee();
                    newEmployee.setId(employeeId);
                    newEmployee.setName(employeeNameField.getText().trim());
                    newEmployee.setDepartmentId(newDepartmentId);
                    newEmployee.setJobCode(newJobCode);
                    newEmployee.setState('t'); // 默认在职 ('t'表示在职)
                    newEmployee.setPassword("123456"); // 默认密码
                    newEmployee.setAuthority("staff"); // 默认权限
                    success = employeeService.addEmployee(newEmployee);
                    message = "新员工加入成功";
                    break;
                case "职务变动":
                    // 更新员工职位
                    Employee empToUpdate = employeeService.getEmployeeById(employeeId);
                    if (empToUpdate != null) {
                        empToUpdate.setJobCode(newJobCode);
                        success = employeeService.updateEmployee(empToUpdate);
                        message = "职务变动成功";
                    }
                    break;
                case "部门变动":
                    // 更新员工部门
                    Employee empToMove = employeeService.getEmployeeById(employeeId);
                    if (empToMove != null) {
                        empToMove.setDepartmentId(newDepartmentId);
                        success = employeeService.updateEmployee(empToMove);
                        message = "部门变动成功";
                    }
                    break;
                case "辞退":
                    // 标记员工为离职
                    Employee empToFire = employeeService.getEmployeeById(employeeId);
                    if (empToFire != null) {
                        empToFire.setState('f'); // 'f'表示离职
                        success = employeeService.updateEmployee(empToFire);
                        message = "员工已辞退";
                    }
                    break;
            }

            // 保存人事变动记录
            if (success) {
                PersonnelChangeService changeService = new PersonnelChangeService();
                if (changeService.addChange(change)) {
                    confirmed = true;
                    SwingUtil.showInfoDialog(this, message);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "保存人事变动记录失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "操作失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "操作发生错误：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public PersonnelChange getPersonnelChange() {
        return change;
    }
    
    // 获取变动类型下拉框 - 用于外部设置默认值
    public JComboBox<String> getChangeTypeCombo() {
        return changeTypeCombo;
    }
    
    // 获取员工ID字段
    public JTextField getEmployeeIdField() {
        return employeeIdField;
    }
    
    // 获取员工姓名字段
    public JTextField getEmployeeNameField() {
        return employeeNameField;
    }
    
    // 获取新部门下拉框
    public JComboBox<CodeNameItem> getDepartmentCombo() {
        return newDepartmentCombo;
    }
    
    // 获取新职位下拉框
    public JComboBox<CodeNameItem> getJobCombo() {
        return newJobCombo;
    }
    
    // 根据部门ID加载职位列表（实际职位与部门无关，直接返回所有职位）
    public List<CodeNameItem> loadJobsByDepartment(int departmentId) throws SQLException {
        List<CodeNameItem> jobs = new ArrayList<>();
        // 职位代码映射（从Employee类的getJobName方法获取）
        String[] jobNames = {"部门经理", "高级工程师", "人事专员", "会计", "销售代表", "行政助理", "初级工程师", "财务主管", "测试工程师", "算法工程师", "市场专员", "销售经理", "后勤主管", "AI 训练师", "数据架构师"};
        for (int i = 1; i <= jobNames.length; i++) {
            jobs.add(new CodeNameItem(i, jobNames[i-1]));
        }
        return jobs;
    }
    
    // 获取变动描述文本区域
    public JTextArea getDescriptionArea() {
        return descriptionArea;
    }
}