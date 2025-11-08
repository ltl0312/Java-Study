package javaprogramming.demo29graphicscomputingsystem;

import java.util.Scanner;

public class ShapeManager {
    public static void main(String[] args) {
        //目标：设计一个图形计算系统，该系统需要计算不同图形的面积和周长。通过接口来定义图形必须实现的功能，展现接口的多态特性和代码复用。
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请选择要计算的图形：");
            System.out.println("1.圆 2.矩形 3.三角形 4.退出");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println("请输入圆的半径：");
                    double radius = sc.nextDouble();
                    if (radius <= 0){
                        System.out.println("输入错误！请重新输入！");
                        continue;
                    }
                    Circle circle = new Circle(radius);
                    System.out.println("请选择要获取的内容：");
                    System.out.println("1.名称 2.周长 3.面积 4.图形信息 5.返回");
                    int choice1 = sc.nextInt();
                    switch (choice1){
                        case 1:
                            System.out.println("名称：" + circle.getShapeName());
                            break;
                        case 2:
                            System.out.println("周长：" + circle.calculatePerimeter());
                            break;
                        case 3:
                            System.out.println("面积：" + circle.calculateArea());
                            break;
                        case 4:
                            circle.displayInfo();
                            break;
                        case 5:
                            System.out.println("已返回！");
                            break;
                        default:
                            System.out.println("输入错误！请重新输入！");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("请输入矩形的宽和高：");
                    double width = sc.nextDouble();
                    double height = sc.nextDouble();
                    if (width <= 0 || height <= 0){
                        System.out.println("输入错误！请重新输入！");
                        continue;
                    }
                    Rectangle rectangle = new Rectangle(width,height);
                    System.out.println("请选择要获取的内容：");
                    System.out.println("1.名称 2.周长 3.面积 4.图形信息 5.返回");
                    int choice2 = sc.nextInt();
                    switch (choice2){
                        case 1:
                            System.out.println("名称：" + rectangle.getShapeName());
                            break;
                        case 2:
                            System.out.println("周长：" + rectangle.calculatePerimeter());
                            break;
                        case 3:
                            System.out.println("面积：" + rectangle.calculateArea());
                            break;
                        case 4:
                            rectangle.displayInfo();
                            break;
                        case 5:
                            System.out.println("已返回！");
                            break;
                        default:
                            System.out.println("输入错误！请重新输入！");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("请输入三角形的三条边：");
                    double a = sc.nextDouble();
                    double b = sc.nextDouble();
                    double c = sc.nextDouble();
                    Triangle triangle = new Triangle(a,b,c);
                    if (a + b <= c || a + c <= b || b + c <= a || a < 0 || b < 0 || c < 0 ||a - c > b || b - c > a || c - a > b || c - b > a || a - b > c || b - a > c){
                        System.out.println("输入错误！请重新输入！");
                        continue;
                    }
                    System.out.println("请选择要获取的内容：");
                    System.out.println("1.名称 2.周长 3.面积 4.图形信息 5.返回");
                    int choice3 = sc.nextInt();
                    switch (choice3){
                        case 1:
                            System.out.println("名称：" + triangle.getShapeName());
                            break;
                        case 2:
                            System.out.println("周长：" + triangle.calculatePerimeter());
                            break;
                        case 3:
                            System.out.println("面积：" + triangle.calculateArea());
                            break;
                        case 4:
                            triangle.displayInfo();
                            break;
                        case 5:
                            System.out.println("已返回！");
                            break;
                        default:
                        System.out.println("输入错误！请重新输入！");
                        break;
                    }
                    break;
                case 4:
                    System.out.println("退出成功！");
                    sc.close();
                    return;
                default:
                    System.out.println("输入错误！请重新输入！");
                    break;
            }
        }


    }
}
