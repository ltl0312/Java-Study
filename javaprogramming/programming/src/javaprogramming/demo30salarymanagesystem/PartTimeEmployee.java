package javaprogramming.demo30salarymanagesystem;

import java.util.Objects;

public class PartTimeEmployee extends Employee{
    // 兼职员工
    private double hourlyWage;
    private double workHours;
    public PartTimeEmployee(int id, String name, double hourlyWage, double workHours) {
        super(id, name);
        this.hourlyWage = hourlyWage;
        this.workHours = workHours;
    }
    @Override
    public double calculateSalary() {
        return hourlyWage * workHours;
    }

    @Override
    public void getEmployeeInfo() {
        System.out.println("兼职员工信息：");
        System.out.println("ID：" + getId());
        System.out.println("姓名：" + getName());
        System.out.println("时薪：" + hourlyWage);
        System.out.println("工作小时数：" + workHours);
        System.out.println("实际工资为：" + calculateSalary());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PartTimeEmployee that = (PartTimeEmployee) o;
        return Double.compare(that.hourlyWage, hourlyWage) == 0 && Double.compare(that.workHours, workHours) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hourlyWage, workHours);
    }
}
