package com.study.netty.proxy.simple;

import java.util.List;

/**
 * Created by piguanghua on 2017/11/15.
 */
public class MyClass {
    public int count;
    public List<String> myList;
    public MyClass(int start) {
        count = start;
    }
    public void increase(int step) {
        count = count + step;
    }
}
