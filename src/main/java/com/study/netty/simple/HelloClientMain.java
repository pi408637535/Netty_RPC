package com.study.netty.simple;

/**
 * Created by piguanghua on 2017/11/15.
 */
public class HelloClientMain {
    public static void main(String[] args) throws Exception {
        HelloServer helloServer = new HelloServerImpl();
        HelloServer helloService = RpcFramework.reference(helloServer, "localhost", 8080);
        for(int i = 0; i < 10; i++){
            System.out.println( helloService.sayHello("a" + i) );
        }
    }
}
