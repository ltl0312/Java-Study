package javaprogramming.demo30salarymanagesystem;

import java.util.Objects;

public class SalesEmployee extends Employee{
    //销售员
    private double baseSalary;
    private double salesCommission;
    public SalesEmployee(int id, String name, double baseSalary, double salesCommission) {
        super(id, name);
        this.baseSalary = baseSalary;
        this.salesCommission = salesCommission;
    }
    @Override
    public double calculateSalary() {
        return baseSalary + salesCommission * 0.05;
    }

    @Override
    public void getEmployeeInfo() {
        System.out.println("销售员信息：");
        System.out.println("ID：" + getId());
        System.out.println("姓名：" + getName());
        System.out.println("基础工资：" + baseSalary);
        System.out.println("销售额：" + salesCommission);
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
        SalesEmployee employee = (SalesEmployee) o;
        return getId() == employee.getId() && Double.compare(employee.baseSalary, baseSalary) == 0 && Double.compare(employee.salesCommission, salesCommission) == 0 && getName().equals(employee.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), baseSalary, salesCommission);
    }
}
