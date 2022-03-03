package com.xiaolong.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.swing.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class NettyServerIncreasePort {

    public final static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 类比IOServer，主要负责创建新的连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 主要用于读取数据以及处理逻辑
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                System.out.println(s + Thread.currentThread().getName());
                            }
                        });
                    }
                });
        int[] port = {1000};
        port = bind(port, serverBootstrap);
        semaphore.acquire();
        System.out.println(port[0]);

    }

    public static int[] bind(int[] port, ServerBootstrap serverBootstrap) {
        ChannelFuture bind = serverBootstrap.bind(port[0]);
        bind.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("绑定端口成功：port =【" + port[0] + "】");
                    semaphore.release();
                } else {
                    ++port[0];
                    bind(port, serverBootstrap);
                }
            }
        });
        return port;
    }
}
