package com.itheima.demo9test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieService {
    //4.准备一个存储容器：存储全部上架的电影数据
    private static ArrayList<Movie> movies = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public void start() {
        while (true) {
            //3.、准备操作界面：GUI界面/cmd命令做。
            System.out.println("====电影信息操作系统===");
            System.out.println("1、上架");
            System.out.println("2、下架");
            System.out.println("3、查询");
            System.out.println("4、封杀");
            System.out.println("5、退出");
            System.out.println("6、显示所有电影");
            System.out.println("请您输入操作命令：");
            String command = sc.next();
            switch (command) {
                case "1":
                    // 上架（独立功能独立成方法）
                    addMovie();
                    break;
                case "2":
                    // 下架
                    break;
                case "3":
                    // 查询
                    queryMovie();
                    break;
                case "4":
                    // 封杀
                    deleteStar();
                    break;
                case "5":
                    System.out.println("退出成功！");
                    return;
                    // 显示所有电影
                case "6":
                    queryAllMovie();
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
        }

    }

    private void queryAllMovie() {
        System.out.println("===========所有电影============");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    /**
     *  根据明星名称删除电影
     * */
    private void deleteStar() {
        System.out.println("===========封杀明星============");
        System.out.println("请您输入要封杀的明星：");
        String star = sc.next();
        int i;
        for (i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getActor().contains(star)) {
                movies.remove(movie);
                System.out.println("封杀成功！");
            }
            i--;
        }
    }

    /**
     *  根据电影名称查询电影
     * */
    private void queryMovie() {
        System.out.println("===========查询电影============");
        System.out.println("请您输入电影名称：");
        String name = sc.next();
        //根据电影名称查询电影对象返回，展示这个对象的数据
        Movie movie = queryMovieByName(name);
        if (movie != null) {
            System.out.println(movie);
            System.out.println("查询成功！");
        } else {
            System.out.println("没有此电影！");
        }
    }

    //根据电影名称查询电影对象返回
    //movies = [m1, m2, m3, ...]
    private Movie queryMovieByName(String name) {
        for (Movie movie : movies) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    private void addMovie() {
        System.out.println("===========上架电影============");
        //分析：每点击一次上架电影，其实就是新增一部电影，每部电影是一个电影对象封装数据的
        //1.创建电影对象，封装这部电影信息。
        Movie movie = new Movie();
        //2.给电影对象注入数据。
        System.out.println("请您输入电影名称：");
        movie.setName(sc.next());
        System.out.println("请您输入主演名称：");
        movie.setActor(sc.next());
        System.out.println("请您输入电影评分：");
        movie.setScore(sc.nextDouble());
        System.out.println("请您输入电影价格：");
        movie.setPrice(sc.nextDouble());
        //3.将电影对象添加到集合中。
        movies.add(movie);
        System.out.println("上架成功！");
    }
}
