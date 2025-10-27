package com.itheima.demo3genericity;

public interface Data<T> {
    void add(T s);
    void delete(T s);
    void update(T s);
    T query(int id);

}
