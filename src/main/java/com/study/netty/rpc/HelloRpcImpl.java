package com.study.netty.rpc;

/**
 * Created by piguanghua on 2017/11/9.
 */
public class HelloRpcImpl implements HelloRpc {

    @Override
    public String hello(String name) {
        return "hello "+name;
    }

}
