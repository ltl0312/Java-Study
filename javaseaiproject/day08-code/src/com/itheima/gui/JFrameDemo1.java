package com.itheima.gui;

import javax.swing.*;
import java.awt.*;

public class JFrameDemo1 {
    public static void main(String[] args) {
        //快速入门一下GUI的Swing编程
        //1.创建一个窗口,有一个输入框,有一个登录按钮
        JFrame win = new JFrame("登录窗口");
        JTextField jtf = new JTextField(10);
        win.setLayout(new FlowLayout());
        win.setSize(300, 200);//设置窗口大小
        win.setLocationRelativeTo(null);//设置窗口居中显示。
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗口的默认操作：退出程序
        JButton jbt = new JButton("登录");
        jbt.setBackground(Color.yellow);
        jbt.setForeground(Color.red);
        jbt.setFont(new Font("微软雅黑", Font.BOLD, 20));
        win.setLayout(new FlowLayout());
        win.add(jtf);
        win.add(jbt);
        win.setVisible(true);
        System.out.println("程序结束");
    }
}
