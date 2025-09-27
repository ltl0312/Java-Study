package com.itheima;

public class App {
    public static void main(String[] args) {
        new MainFrame();
        // 展示历史胜利所用最少步数：
        // 1、在游戏界面上展示：历史胜利的最少步数：0步
        // 2、创建一个文件记录历史最少步数：score.txt 0
        // 3、每次胜利后都要比较当前步数和历史最少步数，如果当前步数小于历史最少步数，则更新历史最少步数
        // 4、每次游戏启动时，都要显示历史最小步数
    }
}
