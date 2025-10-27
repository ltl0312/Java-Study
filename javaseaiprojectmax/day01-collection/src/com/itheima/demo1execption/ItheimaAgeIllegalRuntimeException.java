package com.itheima.demo1execption;

/**
 * 自定义编译时异常
 * 1.继承Exception
 * 2.重写Exception构造方法
 * 3.哪里需要用这个异常返回,哪里就使用throws声明抛出
 */
public class ItheimaAgeIllegalRuntimeException extends RuntimeException {
    public ItheimaAgeIllegalRuntimeException() {
    }

    public ItheimaAgeIllegalRuntimeException(String message) {
        super(message);
    }
}
