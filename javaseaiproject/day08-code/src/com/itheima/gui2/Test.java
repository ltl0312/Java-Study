package com.itheima.gui2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Test {
    public static void main(String[] args) {
        JFrame jf = new JFrame("登录窗口");
        JPanel jb = new JPanel();
        jf.add(jb);
        //JTextField jtf = new JTextField(10);
        jf.setLayout(new FlowLayout());
        jf.setSize(300, 200);//设置窗口大小
        jf.setLocationRelativeTo(null);//设置窗口居中显示。
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗口的默认操作：退出程序
        JButton jbt = new JButton("登录");
        //jf.add(jtf);
        jb.add(jbt);
//        jbt.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(jf, "Stupid!!!");//弹窗
//                System.out.println("登录成功!!!");
//            }
//        });
        jbt.addActionListener((ActionEvent e)->{
            JOptionPane.showMessageDialog(jf, "Stupid!!!");//弹窗
            System.out.println("登录成功!!!");
        });

        //需求:监听用户键盘上下左右四个按键的事件
        jf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();//获取按键的编码
                if (keyCode == KeyEvent.VK_UP) {
                    System.out.println("用户按下了上");
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    System.out.println("用户按下了下");
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    System.out.println("用户按下了左");
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    System.out.println("用户按下了右");
                }
//                else {
//                    System.out.println("用户按下了按键:" + keyCode);
//                }
                System.out.println("========================");
            }
        });

        jf.setVisible(true);//显示窗口
        jf.requestFocus();//让窗口成为焦点
        //System.out.println("程序结束");

    }
}
