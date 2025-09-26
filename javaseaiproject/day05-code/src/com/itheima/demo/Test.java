package com.itheima.demo;

public class Test {
    public static void main(String[] args) {
        //完成面向对象综合案例
        //1.设计电影类
        //2.封装电影数据
        Movie [] movies = new Movie[6];
        //movies = [null, null, null, null, null, null]
        movies[0] = new Movie(1, "唐顿庄园", "热巴", 9.5);
        movies[1] = new Movie(2, "唐顿庄园2", "热巴", 9.5);
        movies[2] = new Movie(3, "唐顿庄园3", "热巴", 9.5);
        movies[3] = new Movie(4, "唐顿庄园4", "热巴", 9.5);
        movies[4] = new Movie(5, "唐顿庄园5", "热巴", 9.5);
        movies[5] = new Movie(6, "唐顿庄园6", "热巴", 9.5);

        //3.创建电影操作对象出来,专门负责
        MovieOperator mo = new MovieOperator( movies);
        mo.printAllMovies();
        mo.searchMovieById();




    }
}
