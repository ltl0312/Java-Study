package javaprogramming.demo30salarymanagesystem;

public class Test {
    public static void main(String[] args) {
        //目标：设计一个员工工资管理系统，该系统需要管理不同类型的员工，并计算他们的工资。系统需要展现继承、多态、抽象类等面向对象特性的深度应用。
        /*
        提示: 1.首先定义抽象员工类 Employee
            • 属性：员工ID、姓名、基础工资
            • 抽象方法：calculateSalary()（计算实际工资）
            • 具体方法：getEmployeeInfo()（获取员工信息）
              2.三种员工类型:员⼯类型、类名、⼯资计算⽅式
            • 全职员工 FullTimeEmployee基础工资 + 绩效奖金
            • 兼职员工PartTimeEmployee时薪 × 工作小时数
            • 销售员SalesEmployee基础工资 + 销售提成（销售额 × 5%）
              3.创建公司类 Company
            • 功能：添加员工、删除员工、计算所有员工的总工资
            • 多态调用所有员工的 calculateSalary() 方法
            • 按工资从低到高排序显示所有员工信息
            • 找出工资最高和最低的员工
              4.要求实现的功能:
            • 使用多态特性处理不同类型员工
            • 使用 Collections.sort() 对员工进行排序
            • 使用 Stream API 进行数据统计
            • 实现正确的 equals() 和 hashCode() 方法
        */

    }
}
