package com.itheima.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 信息管理界面类
public class EmployeeManagementFrame extends JFrame {
    private List<Employee> employees;
    private DefaultListModel<String> listModel;
    private JList<String> employeeList;
    private JTextField searchField;
    private JTextField idField, nameField, genderField, ageField, phoneField, positionField, hireDateField, salaryField, departmentField;

    public EmployeeManagementFrame() {
        setTitle("员工信息管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        employees = new ArrayList<>();
        initializeSampleData(); // 初始化示例数据

        listModel = new DefaultListModel<>();
        employeeList = new JList<>(listModel);
        employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedEmployee();
            }
        });

        JScrollPane listScrollPane = new JScrollPane(employeeList);

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, inputPanel);
        splitPane.setDividerLocation(500);

        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshEmployeeList();
    }

    private void initializeSampleData() {
        employees.add(new Employee("001", "张三", "男", 30, "13800138001", "软件工程师", LocalDate.of(2020, 5, 15), 12000.0, "技术部"));
        employees.add(new Employee("002", "李四", "女", 28, "13800138002", "产品经理", LocalDate.of(2019, 8, 20), 15000.0, "产品部"));
        employees.add(new Employee("003", "王五", "男", 35, "13800138003", "销售经理", LocalDate.of(2018, 3, 10), 10000.0, "销售部"));
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("员工信息"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"ID:", "姓名:", "性别:", "年龄:", "电话:", "职位:", "入职日期:", "薪水:", "部门:"};
        JTextField[] fields = {idField = new JTextField(10), nameField = new JTextField(10), genderField = new JTextField(10),
                ageField = new JTextField(10), phoneField = new JTextField(10), positionField = new JTextField(10),
                hireDateField = new JTextField(10), salaryField = new JTextField(10), departmentField = new JTextField(10)};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
        }

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("按姓名查询:"));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("查询");
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch()); // 按回车也触发查询
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        panel.add(searchPanel, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("添加");
        JButton updateButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");
        JButton clearButton = new JButton("清空");
        JButton refreshButton = new JButton("刷新");

        addButton.addActionListener(e -> addEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        clearButton.addActionListener(e -> clearFields());
        refreshButton.addActionListener(e -> refreshEmployeeList());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(refreshButton);

        return panel;
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            refreshEmployeeList(); // 如果查询为空，显示所有员工
            return;
        }

        List<Employee> filtered = employees.stream()
                .filter(emp -> emp.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        listModel.clear();
        for (Employee emp : filtered) {
            listModel.addElement(emp.toString());
        }
    }

    private void refreshEmployeeList() {
        listModel.clear();
        for (Employee emp : employees) {
            listModel.addElement(emp.toString());
        }
        searchField.setText(""); // 清空查询框
    }

    private void loadSelectedEmployee() {
        int index = employeeList.getSelectedIndex();
        if (index >= 0) {
            String selectedStr = listModel.getElementAt(index);
            // 从字符串中解析出ID，然后找到对应的Employee对象
            String id = selectedStr.split(" \\| ")[0].split(": ")[1];
            Employee emp = employees.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
            if (emp != null) {
                idField.setText(emp.getId());
                nameField.setText(emp.getName());
                genderField.setText(emp.getGender());
                ageField.setText(String.valueOf(emp.getAge()));
                phoneField.setText(emp.getPhone());
                positionField.setText(emp.getPosition());
                hireDateField.setText(emp.getHireDate().toString());
                salaryField.setText(String.valueOf(emp.getSalary()));
                departmentField.setText(emp.getDepartment());
            }
        }
    }

    private void addEmployee() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String gender = genderField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String phone = phoneField.getText().trim();
            String position = positionField.getText().trim();
            LocalDate hireDate = LocalDate.parse(hireDateField.getText().trim());
            double salary = Double.parseDouble(salaryField.getText().trim());
            String department = departmentField.getText().trim();

            if (employees.stream().anyMatch(emp -> emp.getId().equals(id))) {
                JOptionPane.showMessageDialog(this, "员工ID已存在！", "添加失败", JOptionPane.ERROR_MESSAGE);
                return;
            }

            employees.add(new Employee(id, name, gender, age, phone, position, hireDate, salary, department));
            refreshEmployeeList();
            clearFields();
            JOptionPane.showMessageDialog(this, "员工添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "请输入正确的数据格式！", "添加失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() {
        int selectedIndex = employeeList.getSelectedIndex();
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一个员工进行修改！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Employee emp = employees.get(selectedIndex);
            emp.setId(idField.getText().trim());
            emp.setName(nameField.getText().trim());
            emp.setGender(genderField.getText().trim());
            emp.setAge(Integer.parseInt(ageField.getText().trim()));
            emp.setPhone(phoneField.getText().trim());
            emp.setPosition(positionField.getText().trim());
            emp.setHireDate(LocalDate.parse(hireDateField.getText().trim()));
            emp.setSalary(Double.parseDouble(salaryField.getText().trim()));
            emp.setDepartment(departmentField.getText().trim());

            refreshEmployeeList();
            clearFields();
            JOptionPane.showMessageDialog(this, "员工信息修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "请输入正确的数据格式！", "修改失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        int selectedIndex = employeeList.getSelectedIndex();
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一个员工进行删除！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的员工吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            employees.remove(selectedIndex); // 从列表中删除
            refreshEmployeeList();
            clearFields();
            JOptionPane.showMessageDialog(this, "员工删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        genderField.setText("");
        ageField.setText("");
        phoneField.setText("");
        positionField.setText("");
        hireDateField.setText("");
        salaryField.setText("");
        departmentField.setText("");
        employeeList.clearSelection();
    }
}



