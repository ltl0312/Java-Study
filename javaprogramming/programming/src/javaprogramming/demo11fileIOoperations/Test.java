package javaprogramming.demo11fileIOoperations;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception{
        //目标：：编写一个程序，将用户输入的内容写入文件，并从文件中读取该内容并输出到控制台。
        FileWriter fw = new FileWriter("programming\\src\\javaprogramming\\demo11fileIOoperations\\test.txt");
        byte[] bytes = new byte[1024];
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入内容：");
        String content = sc.nextLine();
        fw.write(content);
        System.out.println("写入成功");
        fw.close();
        FileReader fr = new FileReader("programming\\src\\javaprogramming\\demo11fileIOoperations\\test.txt");
        System.out.println("读取内容：");
        char[] chars = new char[1024];
        fr.read(chars);
        System.out.println(new String(chars));
        fr.close();

    }
}
