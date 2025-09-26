package com.itheima.array;

public class Computer {
    private String cpu; // cpu
    private String memory; // 内存
    public String screen; // 屏幕
    String brand; // 品牌---->default属性
    public Computer(String brand, String cpu, String memory, String screen) {
        this.brand = brand;
        this.cpu = cpu;
        this.memory = memory;
        this.screen = screen;
    }
    public void Boot(){
        System.out.println("开机~~~");
    }
    public void PowerOff(){
        System.out.println("关机~~~");
    }
    public void SurfInternet(){
        System.out.println("上网~~~");
    }
}
