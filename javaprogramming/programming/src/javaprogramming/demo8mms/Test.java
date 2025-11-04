// ProductManager.java
package javaprogramming.demo8mms;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：：定义Product类，包含id、name、price等属性，创建一个商品列表，并实现查询和添加商品的功能。
        Product p1 = new Product("电脑", 5000, 1);
        Product p2 = new Product("鼠标", 10, 2);
        Product p3 = new Product("键盘", 20, 3);
        Product p4 = new Product("显示器", 1000, 4);
        Product p5 = new Product("USB", 5, 5);
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        System.out.println("欢迎来到商品管理系统");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.查询商品");
            System.out.println("2.添加商品");
            System.out.println("3.退出");
            System.out.println("请输入操作：");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("请输入查询方法：");
                    System.out.println("1.根据ID查询");
                    System.out.println("2.根据名称查询");
                    System.out.println("3.显示所有商品");
                    System.out.println("4.返回");
                    int choice1 = sc.nextInt();
                    switch (choice1) {
                        case 1:
                            System.out.println("请输入ID：");
                            int id = sc.nextInt();
                            ProductManager.findProductById(products, id);
                            break;
                        case 2:
                            System.out.println("请输入名称：");
                            String name = sc.next();
                            ProductManager.findProductsByName(products, name);
                            break;
                        case 3:
                            ProductManager.displayAllProducts(products);
                            break;
                        case 4:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("请输入商品名称：");
                    String name = sc.next();
                    System.out.println("请输入商品价格：");
                    double price = sc.nextDouble();
                    System.out.println("请输入商品ID：");
                    int id1 = sc.nextInt();
                    ProductManager.addProduct(products, name, price, id1);
                    break;
                case 3:
                    System.out.println("成功退出");
                    return;
                default:
                    System.out.println("输入错误");
                    System.out.println("请重新输入：");
                    break;
            }
        }

    }
}

