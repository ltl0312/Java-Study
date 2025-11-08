package javaprogramming.demo29graphicscomputingsystem;

public class Rectangle implements Shape{
    //矩形
    private double width;
    private double height;

    public Rectangle(double width,double height){
        this.width = width;
        this.height = height;
    }
    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String getShapeName() {
        return "矩形";
    }

    @Override
    public void displayInfo() {
        System.out.println("名称：" + getShapeName());
        System.out.println("宽：" + width + " 高：" + height);
        System.out.println("周长：" + calculatePerimeter());
        System.out.println("面积：" + calculateArea());
    }

}
