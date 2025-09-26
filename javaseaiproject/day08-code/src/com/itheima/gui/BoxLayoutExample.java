package com.itheima.gui;

import javax.swing.*;
import java.awt.*;

public class BoxLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BoxLayout Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 垂直排列

        panel.add(new JButton("Button 1"));
        panel.add(Box.createVerticalStrut(10)); // 添加垂直间隔
        panel.add(new JButton("Button 2"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JButton("Button 3"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JButton("Button 4"));

        frame.add(panel);
        frame.setVisible(true);
    }
}