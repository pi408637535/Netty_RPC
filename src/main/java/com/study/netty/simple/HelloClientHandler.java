package com.study.netty.simple;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by piguanghua on 2017/11/14.
 */
@ChannelHandler.Sharable
public class HelloClientHandler  extends ChannelInboundHandlerAdapter {
    private Object response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response=msg;
        System.out.println("client接收到服务器返回的消息:" + msg);
    }

    public Object getResponse() {
        return response;
    }
}
