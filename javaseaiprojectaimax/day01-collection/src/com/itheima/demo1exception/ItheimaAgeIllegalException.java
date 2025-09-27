package com.itheima.demo1exception;

/**
 * 自定义的编译时异常
 * 1、继承Exception做爸爸。
 * 2、重写Exception的构造器。
 * 3、哪里需要用这个异常返回，哪里就throw
 */
public class ItheimaAgeIllegalException extends Exception{
    public ItheimaAgeIllegalException() {
    }

    public ItheimaAgeIllegalException(String message) {
        super(message);
    }
}
