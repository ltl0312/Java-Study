package com.pms.utils;

import javax.swing.*;
import java.awt.*;

public class SwingUtil {
    /**
     * 获取组件所在的顶级窗口
     */
    public static Frame getParentFrame(Component component) {
        if (component instanceof Frame) {
            return (Frame) component;
        }

        while (component.getParent() != null) {
            component = component.getParent();
            if (component instanceof Frame) {
                return (Frame) component;
            }
        }

        return null;
    }

    /**
     * 创建带图标的按钮
     */
    public static JButton createIconButton(String text, String iconPath) {
        JButton button = new JButton(text);
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(iconPath);
            if (icon.getIconWidth() > 0) { // 图标存在
                button.setIcon(icon);
            }
        }
        return button;
    }

    /**
     * 显示错误消息对话框
     */
    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "错误", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 显示信息消息对话框
     */
    public static void showInfoDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 显示确认对话框
     */
    public static boolean showConfirmDialog(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "确认", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}