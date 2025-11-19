package javaprogramming.demo30salarymanagesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：设计一个员工工资管理系统，该系统需要管理不同类型的员工，并计算他们的工资。系统需要展现继承、多态、抽象类等面向对象特性的深度应用。

        List<Employee> employees = new ArrayList<>();
        employees.add(new FullTimeEmployee(1, "张三", 5000, 1000));
        employees.add(new PartTimeEmployee(2, "李四", 20, 8));
        employees.add(new SalesEmployee(3, "王五", 3000, 5000));

        Company company = new Company(employees.toArray(new Employee[0]));
        Scanner scanner = new Scanner(System.in);
        System.out.println("======欢迎使用员工工资管理系统======");
        while (true) {
            System.out.println("1.添加员工");
            System.out.println("2.删除员工");
            System.out.println("3.计算所有员工的工资");
            System.out.println("4.对员工工资进行排序");
            System.out.println("5.统计员工工资数据");
            System.out.println("6.查看员工信息");
            System.out.println("7.工资最低和最高的员工");
            System.out.println("8.退出");
            System.out.print("请选择操作：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("请输入员工ID：");
                    int id = scanner.nextInt();
                    System.out.print("请输入员工姓名：");
                    String name = scanner.next();
                    System.out.print("请输入员工类型（1.全职员工 2.兼职员工 3.销售员）：");
                    int type = scanner.nextInt();
                    switch (type) {
                        case 1:
                            System.out.print("请输入员工基础工资：");
                            double baseSalary = scanner.nextDouble();
                            System.out.print("请输入员工奖金：");
                            double bonus = scanner.nextDouble();
                            company.addEmployee(new FullTimeEmployee(id, name, baseSalary, bonus));
                            System.out.println("添加员工成功！");
                            break;
                        case 2:
                            System.out.print("请输入员工时薪：");
                            double hourlyWage = scanner.nextDouble();
                            System.out.print("请输入员工工作小时数：");
                            double workHours = scanner.nextDouble();
                            company.addEmployee(new PartTimeEmployee(id, name, hourlyWage, workHours));
                            System.out.println("添加员工成功！");
                            break;
                        case 3:
                            System.out.print("请输入员工基础工资：");
                            double baseSalary2 = scanner.nextDouble();
                            System.out.print("请输入员工销售额：");
                            double sales = scanner.nextDouble();
                            company.addEmployee(new SalesEmployee(id, name, baseSalary2, sales));
                            System.out.println("添加员工成功！");
                            break;
                        default:
                            System.out.println("无效的选择！");
                            break;
                    }
                    break;
                case 2:
                    System.out.print("请输入员工ID：");
                    int deleteId = scanner.nextInt();
                    company.deleteEmployee(deleteId);
                    System.out.println("删除员工成功！");
                    break;
                case 3:
                    company.calculateAllSalaries();
                    break;
                case 4:
                    company.sortEmployees();
                    break;
                case 5:
                    company.countSalary();
                    break;
                case 6:
                    System.out.println("请选择查询方式：");
                    System.out.println("1.按ID查询");
                    System.out.println("2.按姓名查询");
                    System.out.println("3.按类型查询");
                    System.out.println("4.查看所有员工信息");
                    int queryType = scanner.nextInt();
                    int sign = 0;
                    switch (queryType) {
                        case 1:
                            System.out.print("请输入员工ID：");
                            int queryId = scanner.nextInt();
                            for (Employee employee : employees) {
                                if (employee.getId() == queryId) {
                                    employee.getEmployeeInfo();
                                    sign = 1;
                                    break;
                                }
                            }
                            if (sign == 0) {
                                System.out.println("未找到该员工！");
                            }
                            break;
                        case 2:
                            System.out.print("请输入员工姓名：");
                            String queryName = scanner.next();
                            for (Employee employee : employees) {
                                if (employee.getName().equals(queryName)) {
                                    employee.getEmployeeInfo();
                                    sign = 1;
                                }
                            }
                            if (sign == 0) {
                                System.out.println("未找到该员工！");
                            }
                            break;
                        case 3:
                            System.out.println("请选择员工类型：");
                            System.out.println("1.全职员工");
                            System.out.println("2.兼职员工");
                            System.out.println("3.销售员");
                            int queryType2 = scanner.nextInt();
                            switch (queryType2) {
                                case 1:
                                    for (Employee employee : employees) {
                                        if (employee instanceof FullTimeEmployee) {
                                            employee.getEmployeeInfo();
                                            sign = 1;
                                        }
                                    }
                                    break;
                                case 2:
                                    for (Employee employee : employees) {
                                        if (employee instanceof PartTimeEmployee) {
                                            employee.getEmployeeInfo();
                                            sign = 1;
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Employee employee : employees) {
                                        if (employee instanceof SalesEmployee) {
                                            employee.getEmployeeInfo();
                                            sign = 1;
                                        }
                                    }
                                    break;
                            }
                            if (sign == 0) {
                                System.out.println("未找到该员工！");
                            }
                            break;
                        case 4:
                            for (Employee employee : employees) {
                                employee.getEmployeeInfo();
                            }
                            break;
                    }
                    break;
                case 7:
                    company.findMaxAndMinSalary();
                    break;
                case 8:
                    System.out.println("退出系统！");
                    return;
                default:
                    System.out.println("无效的选择！");
                    break;
            }
            System.out.println("---------------------------------");
        }


    }
}
