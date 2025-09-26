package com.itheima.demo;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        //目标:面向对象实现智能家居控制系统
        //角色:设备(吊灯,电视机,洗衣机,落地窗,......)
        //具备的功能:开和关
        //谁控制它们:智能控制系统(单例对象),控制调用设备的开和关功能
        //1.定义设备类
        //2.定义设备对象,放到数组中
        JD[] jds = new JD[4];
        jds[0] = new TV("电视机",false);
        jds[1] = new WashMachine("洗衣机",false);
        jds[2] = new Lamp("吊灯",false);
        jds[3] = new Air("空调",false);

        //3.为每个设备制定开和关的功能,定义一个接口

        //4.创建智能控制对象,控制设备的开和关功能
        //SmartHomeControl smartHomeControl = new SmartHomeControl();
        SmartHomeControl smartHomeControl = SmartHomeControl.getInstance();

        //5.控制电视机
        //smartHomeControl.control(jds[0]);
        //6.提示用户操作:a.展示全部设备的当前情况;b.让用户选择哪一个操作
        //打印全部设备的开和关的现状
        while (true){
            smartHomeControl.printAllStatus(jds);
            System.out.println("请选择您要操作的设备编号:");
            Scanner sc = new Scanner(System.in);
            String command = sc.next();
            switch (command){
                case "1":
                    smartHomeControl.control(jds[0]);
                    break;
                case "2":
                    smartHomeControl.control(jds[1]);
                    break;
                case "3":
                    smartHomeControl.control(jds[2]);
                    break;
                case "4":
                    smartHomeControl.control(jds[3]);
                    break;
                case "exit":
                    System.out.println("退出系统");
                    return;
                default:
                    System.out.println("输入错误");
                    break;
            }
        }
    }
}
