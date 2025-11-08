package javaprogramming.demo30salarymanagesystem;

import java.util.Objects;

public class FullTimeEmployee extends Employee{
    //全职员工
    private double baseSalary;
    private double bonus;
    public FullTimeEmployee(int id, String name, double baseSalary, double bonus) {
        super(id, name);
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }
    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }

    @Override
    public void getEmployeeInfo() {
        System.out.println("全职员工信息：");
        System.out.println("ID：" + getId());
        System.out.println("姓名：" + getName());
        System.out.println("基础工资：" + baseSalary);
        System.out.println("奖金：" + bonus);
        System.out.println("实际工资：" + calculateSalary());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullTimeEmployee employee = (FullTimeEmployee) o;
        return getId() == employee.getId() && Double.compare(employee.baseSalary, baseSalary) == 0 && Double.compare(employee.bonus, bonus) == 0 && getName().equals(employee.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), baseSalary, bonus);
    }
}
