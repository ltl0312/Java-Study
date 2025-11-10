package javaprogramming.demo31librarymanagementsystem;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标：实现一个简单的图书管理系统，支持以下功能
        OperateBook operateBook = new OperateBook();
        Scanner scanner = new Scanner(System.in);
        System.out.println("===欢迎来到图书管理系统===");
        while (true) {
            System.out.println("1. 添加图书");
            System.out.println("2. 查看所有图书");
            System.out.println("3. 借书");
            System.out.println("4. 还书");
            System.out.println("5. 排序");
            System.out.println("6. 保存图书");
            System.out.println("7. 退出");
            System.out.print("请选择：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("请输入图书的ISBN编号：");
                    String ISBN = scanner.next();
                    System.out.print("请输入图书的书名：");
                    String name = scanner.next();
                    System.out.print("请输入图书的作者：");
                    String author = scanner.next();
                    System.out.print("请输入图书的库存数量：");
                    int stock = scanner.nextInt();
                    Books book = new Books(ISBN, name, author, stock);
                    operateBook.addBook(book);
                    System.out.println("添加图书成功！");
                    break;
                case 2:
                    operateBook.showAllBooks();
                    break;
                case 3:
                    System.out.print("请输入要借书的ISBN编号：");
                    ISBN = scanner.next();
                    operateBook.borrowBook(ISBN);
                    break;
                case 4:
                    System.out.print("请输入要还书的ISBN编号：");
                    ISBN = scanner.next();
                    operateBook.returnBook(ISBN);
                    break;
                case 5:
                    operateBook.sortBooks();
                    System.out.println("图书信息已排序！");
                case 6:
                    operateBook.saveBooks();
                    System.out.println("图书信息保存成功！");
                    break;
                case 7:
                    System.out.println("感谢使用图书管理系统！");
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效的选择，请重新输入！");
                    break;

            }
        }

    }
}
