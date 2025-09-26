package com.itheima.gui;

import javax.swing.*;
import java.awt.*;

public class FlowLayoutExample {
    public static void main(String[] args) {
        JFrame jf = new JFrame("流式布局");
        jf.setSize(400, 300);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setLayout(new FlowLayout());//设置流式布局管理器
        for (int i = 0; i < 10; i++) {
            JButton jbt = new JButton("按钮" + i);
            jf.add(jbt);
        }
        jf.setVisible(true);
    }
}
