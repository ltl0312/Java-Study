package com.itheima.gui2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//自定义的登陆界面,继承JFrame
public class LoginFrame extends JFrame implements ActionListener {
    public LoginFrame() {
        //设置窗体的标题
        this.setTitle("登录界面");
        //设置窗体的宽高
        this.setSize(400, 300);
        //设置窗体的位置
        this.setLocationRelativeTo(null);
        //设置窗体的关闭模式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体的布局管理器
        this.setLayout(new FlowLayout());
        //创建一个文本框
        JTextField jtf = new JTextField(10);

        init();//初始化窗口组件

    }
    private void init() {
        //添加一个登录按钮
        JButton jbt = new JButton("登录");
        jbt.addActionListener(this);
        JPanel panel = new JPanel();
        this.add(panel);
        panel.add(jbt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Stupid!!!");
    }
}
