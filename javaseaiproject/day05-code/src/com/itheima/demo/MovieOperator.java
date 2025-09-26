package com.itheima.demo;

import java.util.Scanner;

// 电影操作类
public class MovieOperator {
    private Movie[] movies;

    public MovieOperator(Movie[] movies) {
        this.movies = movies;
    }

    public void printAllMovies() {
        System.out.println("==============全部电影信息=============");
        for (int i = 0; i < movies.length; i++) {
            Movie m = movies[i];
            System.out.println(m.getId() + "\t" + m.getName() + '\t' + m.getActor() + '\t' + m.getPrice() + '\t');
        }
    }

    // 根据id查询电影
    public void searchMovieById() {
        System.out.println("请输入要查询的id：");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        for (int i = 0; i < movies.length; i++) {
            Movie m = movies[i];
            if (m.getId() == id) {
                System.out.println(m.getId() + "\t" + m.getName() + '\t' + m.getActor() + '\t' + m.getPrice() + '\t');
                return;
            }
        }
    }
}
