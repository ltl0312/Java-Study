package javaprogramming.demo29graphicscomputingsystem;

public class Triangle implements Shape{
    //三角形
    private double a;
    private double b;
    private double c;
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Override
    public double calculateArea() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
    @Override
    public double calculatePerimeter() {
        return a + b + c;
    }
    @Override
    public String getShapeName() {
        return "三角形";
    }
    @Override
    public void displayInfo() {
        System.out.println("三角形的名称为：" + getShapeName());
        System.out.println("三角形的边长为：" + a + " " + b + " " + c);
        System.out.println("三角形的周长为：" + calculatePerimeter());
        System.out.println("三角形的面积为：" + calculateArea());
    }
}
