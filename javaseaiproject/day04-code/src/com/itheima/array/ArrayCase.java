package com.itheima.array;

public class ArrayCase {
    public static void main(String[] args) {
        start();

    }

    public static void start() {
        //1.做牌
        String [] poker = new String[54];
        String [] colors = {"♠","♥","♣","♦"};
        String [] numbers = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        int index = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < colors.length; j++) {
                poker[index] = numbers[i] + colors[j];
                index++;
            }
        }
        poker[index] = "小王";
        poker[index + 1] = "大王";
        //打印牌
        for (int i = 0; i < poker.length; i++) {
            System.out.print(poker[i] + "\t");
        }
        System.out.println();
        //2.洗牌
        for (int i = 0; i < poker.length; i++) {
            int index1 = (int)(Math.random() * poker.length);
            String temp = poker[i];
            poker[i] = poker[index1];
            poker[index1] = temp;
            System.out.print(poker[i] + "\t");

        }
        System.out.println();
    }
}
