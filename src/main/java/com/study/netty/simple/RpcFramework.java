package com.study.netty.simple;

import com.study.netty.rpc.ClassInfo;
import com.study.netty.study.ClientHandler;
import com.study.netty.study.ServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by piguanghua on 2017/11/10.
 */
public class RpcFramework {
    public static void export(final Object server,int port) throws Exception{
        System.out.println("export server" + server.getClass().getName() + "on " + port);
        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();
        //boss和worker
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //设置线程池
            bootstrap.group(boss, worker);
            //设置socket工厂、
            bootstrap.channel(NioServerSocketChannel.class);
            //设置管道工厂
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                    pipeline.addLast(new LengthFieldPrepender(4));
                    pipeline.addLast("encoder", new ObjectEncoder());
                    pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                    ch.pipeline().addLast(new HelloServerHandler());
                }
            });
            //设置参数，TCP参数
            bootstrap.option(ChannelOption.SO_BACKLOG, 2048);//serverSocketchannel的设置，链接缓冲池的大小
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//socketchannel的设置,维持链接的活跃，清除死链接
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);//socketchannel的设置,关闭延迟发送
            //绑定端口
            ChannelFuture future = bootstrap.bind("127.0.0.1",port);

            System.out.println("start");

            //等待服务端关闭
            future.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        } finally{
            //释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T reference(final Object object, final String host, final int port){

        HelloClientHandler helloClientHandler =  new HelloClientHandler();

        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces() , new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Bootstrap bootstrap = new Bootstrap();
                EventLoopGroup worker = new NioEventLoopGroup();
                bootstrap.group(worker);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.handler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast(helloClientHandler);
                    }
                });
                ChannelFuture future = bootstrap.connect(host, port).sync();;

                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassName(object.getClass().getName());
                classInfo.setMethodName(method.getName());
                classInfo.setObjects(args);
                classInfo.setTypes(method.getParameterTypes());

                future.channel().writeAndFlush(classInfo).sync();
                future.channel().closeFuture().sync();

                return helloClientHandler.getResponse();
            }
        });
    }
}
