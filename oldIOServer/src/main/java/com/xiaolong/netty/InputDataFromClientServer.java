package com.xiaolong.netty;

import com.xiaolong.netty.handler.FirstServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class InputDataFromClientServer {

    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;

    @Before
    public void init(){
        serverBootstrap = new ServerBootstrap();
        // 类比IOServer，主要负责创建新的连接
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
    }

    @Test
    public void testInputDataFromClientServer() throws InterruptedException {
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });

        bind(8006, serverBootstrap);

        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }

    private void bind(int port, ServerBootstrap serverBootstrap) throws InterruptedException {
        NettyServerIncreasePort.bind(new int[]{port}, serverBootstrap);
        NettyServerIncreasePort.semaphore.acquire();
        System.out.println(port);
    }
}
