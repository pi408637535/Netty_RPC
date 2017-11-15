package com.study.netty.simple;

/**
 * Created by piguanghua on 2017/11/10.
 */
public class HelloServerImpl implements HelloServer {
    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
