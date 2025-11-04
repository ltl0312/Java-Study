package com.itheima.demo3annotation;
/**
 * @author LTL
 */ // 自定义注解
public @interface MyBook {
    String name();
    int age() default 18;
    String[] address();
}
