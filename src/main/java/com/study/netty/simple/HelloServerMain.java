package com.study.netty.simple;

/**
 * Created by piguanghua on 2017/11/15.
 */
public class HelloServerMain {
    public static void main(String[] args) throws Exception {
        HelloServer helloServer = new HelloServerImpl();
        RpcFramework.export(helloServer, 8080);
    }
}
