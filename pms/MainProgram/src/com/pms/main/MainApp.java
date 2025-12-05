package com.pms.main;

import com.pms.gui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MainApp {
    public static void main(String[] args) {
        try {
            // 设置美观的Nimbus外观
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 在事件调度线程中启动GUI
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}