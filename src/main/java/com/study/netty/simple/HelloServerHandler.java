package com.study.netty.simple;

import com.study.netty.rpc.ClassInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * Created by piguanghua on 2017/11/10.
 */
public class HelloServerHandler  extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClassInfo classInfo = (ClassInfo)msg;
        Object claszz = Class.forName(classInfo.getClassName()).newInstance();
        Method method = claszz.getClass().getMethod(classInfo.getMethodName(), classInfo.getTypes());
        Object result = method.invoke(claszz, classInfo.getObjects());
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

}
