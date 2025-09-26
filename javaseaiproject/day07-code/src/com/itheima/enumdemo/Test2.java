package com.itheima.enumdemo;

public class Test2 {
    public static void main(String[] args) {
        //目标:掌握枚举类的应用场景:做信息的分类和标志
        //需求:模拟上下左右移动图片
        //第一种用常量
        //move(Contant.UP);
        //第二种用枚举
        move2(Direction.UP);
    }

    public static void move2(Direction direction) {
        switch (direction) {
            case UP:
                System.out.println("向上移动");
                 break;
            case DOWN:
                System.out.println("向下移动");
                break;
            case direction.LEFT:
                System.out.println("向左移动");
                break;
            case direction.RIGHT:
                System.out.println("向右移动");
                break;
            default:
                System.out.println("输入的移动方向有误");
                break;
        }
    }

    public static void move(int direction) {
        switch (direction) {
            case Contant.UP:
                System.out.println("向上移动");
                break;
            case Contant.DOWN:
                System.out.println("向下移动");
                break;
            case Contant.LEFT:
                System.out.println("向左移动");
                break;
            case Contant.RIGHT:
                System.out.println("向右移动");
                break;
            default:
                System.out.println("输入的移动方向有误");
                break;
        }
    }
}
