package javaprogramming.demo8mms;

import java.util.List;

public class ProductManager {
    // 添加商品
    public static void addProduct(List<Product> products, String name, double price, int id){
        Product p = new Product(name, price, id);
        products.add(p);
        System.out.println("添加成功");
    }
    // 根据id查询商品
    public static void findProductById(List<Product> products, int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                System.out.println(p);
                return;
            }
        }
        System.out.println("没有此商品");
    }

    // 根据名称查询商品
    public static void findProductsByName(List<Product> products, String name) {
        for (Product p : products) {
            if (p.getName().contains(name)) {
                System.out.println(p);
            }
        }
        System.out.println("没有此商品");
    }

    // 显示所有商品
    public static void displayAllProducts(List<Product> products) {
        for (Product p : products) {
            System.out.println(p);
        }
    }




}
