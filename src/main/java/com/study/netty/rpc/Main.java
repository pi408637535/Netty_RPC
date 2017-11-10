package com.study.netty.rpc;

/**
 * Created by piguanghua on 2017/11/9.
 */
public class Main {
    public static void main(String [] args){
        HelloRpc helloRpc = new HelloRpcImpl();
        helloRpc = RPCProxy.create(helloRpc);
        System.err.println(helloRpc.hello("rpc"));
    }
}
