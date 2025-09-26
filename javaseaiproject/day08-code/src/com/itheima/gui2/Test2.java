package com.itheima.gui2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test2 {
    public static void main(String[] args) {
        //第一种写法:提供实现类:创建实现类对象创建事件监听器
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

        jbt.addActionListener(new MyActionListener(jf));

        jf.setVisible(true);
    }
}
//实现类
class MyActionListener implements ActionListener{
    private JFrame jf;
    public MyActionListener(JFrame jf){
        this.jf = jf;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(jf, "Stupid!!!");//弹窗
        System.out.println("登录成功!!!");
    }
}
