package javaprogramming.demo29graphicscomputingsystem;

public class Circle implements Shape{
    //圆
    private double radius;
    public Circle(double radius){
        this.radius = radius;
    }
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String getShapeName() {
        return "圆";
    }

    @Override
    public void displayInfo() {
        System.out.println("名称：" + getShapeName());
        System.out.println("半径：" + radius);
        System.out.println("周长：" + calculatePerimeter());
        System.out.println("面积：" + calculateArea());
    }


}
