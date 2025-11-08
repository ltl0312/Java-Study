package javaprogramming.demo20countwords;

import java.io.FileReader;

public class Test {
    public static void main(String[] args) throws Exception {
        //目标：编写程序，读取一个文本文件，统计其中的单词数量。
        FileReader fr = new FileReader("D:\\Code\\Java\\javaprogramming\\programming\\src\\javaprogramming\\demo20countwords\\text");
        char[] chars = new char[1024];
        fr.read(chars);
        String text = new String(chars);
        String[] words = text.split("[\\s\\p{Punct}]+(?![^\\s\\p{Punct}]*$)");
        System.out.println("单词数量为：" + words.length);
        fr.close();
    }
}
