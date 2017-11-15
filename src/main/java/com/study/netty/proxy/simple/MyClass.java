package com.study.netty.proxy.simple;

/**
 * Created by piguanghua on 2017/11/15.
 */
public class MyClass {
    public int count;
    public MyClass(int start) {
        count = start;
    }
    public void increase(int step) {
        count = count + step;
    }
}
