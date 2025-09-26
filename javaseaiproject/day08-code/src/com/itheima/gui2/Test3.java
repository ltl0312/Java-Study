package com.itheima.gui2;

import javax.swing.*;

public class Test3 {
    public static void main(String[] args) {
        //自定义一个登录界面,让界面对象本身也是事件监听器对象
        LoginFrame jf = new LoginFrame();//创建登录界面对象
        jf.setVisible(true);
    }
}
