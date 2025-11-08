package javaprogramming.demo30salarymanagesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Company {
    ArrayList<Employee> employees;
    public Company(Employee... employees) {
        this.employees = new ArrayList<>(Arrays.asList(employees));
    }
    //添加员工
    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("添加员工成功！");
    }
    //删除员工
    public void deleteEmployee(int id) {
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = (Employee) employees.get(i);
            if (employee.getId() == id) {
                employees.remove(i);
                System.out.println("删除员工成功！");
            }
        }
    }
    //计算所有员工的工资
    public void calculateAllSalaries() {
        for (Employee employee : employees) {
            System.out.println("员工" + employee.getName() + "的工资为：" + employee.calculateSalary());
            employee.getEmployeeInfo();
        }
    }

    //使用 Collections.sort() 对员工进行排序
    public void sortEmployees() {
        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return Double.compare(o1.calculateSalary(), o2.calculateSalary());
            }
        });
        System.out.println("员工排序后：");
        for (Employee employee : employees) {
            employee.getEmployeeInfo();
        }
    }

    //使用 Stream API 进行数据统计
    public void countSalary() {
        long count = (long) employees.stream()
                .filter(employee -> employee instanceof FullTimeEmployee)
                .mapToDouble(Employee::calculateSalary)
                .sum();
        System.out.println("全职员工的总工资为：" + count);
        long count2 = (long) employees.stream()
                .filter(employee -> employee instanceof PartTimeEmployee)
                .mapToDouble(Employee::calculateSalary)
                .sum();
                System.out.println("兼职员工的总工资为：" + count2);
        long count3 = (long) employees.stream()
                .filter(employee -> employee instanceof SalesEmployee)
                .mapToDouble(Employee::calculateSalary)
                .sum();
        System.out.println("销售员的总工资为：" + count3);
    }

    //找出工资最高和最低的员工
    public void findMaxAndMinSalary() {
        Employee maxSalaryEmployee = employees.stream()
                .max(Comparator.comparingDouble(Employee::calculateSalary))
                .orElse(null);
                System.out.println("工资最高员工为：" + maxSalaryEmployee.getName());
                System.out.println("工资最高员工工资为：" + maxSalaryEmployee.calculateSalary());

                Employee minSalaryEmployee = employees.stream()
                .min(Comparator.comparingDouble(Employee::calculateSalary))
                .orElse(null);
                System.out.println("工资最低员工为：" + minSalaryEmployee.getName());
                System.out.println("工资最低员工工资为：" + minSalaryEmployee.calculateSalary());
    }



}
