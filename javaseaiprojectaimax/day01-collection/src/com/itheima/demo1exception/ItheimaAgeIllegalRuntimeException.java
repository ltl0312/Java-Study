package com.itheima.demo1exception;

/**
 * 自定义的运行时异常
 * 1、继承RuntimeException做爸爸。
 * 2、重写RuntimeException的构造器。
 * 3、哪里需要用这个异常返回，哪里就throw
 */
public class ItheimaAgeIllegalRuntimeException extends RuntimeException{
    public ItheimaAgeIllegalRuntimeException() {
    }

    public ItheimaAgeIllegalRuntimeException(String message) {
        super(message);
    }
}
