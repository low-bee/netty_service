package com.xiaolong.netty;

import com.xiaolong.netty.handler.FirstServerChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Description: 第一个服务
 * @Author xiaolong
 * @Date 2022/3/5 12:02 上午
 */
public class FirstServer {

    public static void main(String[] args) {
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
                        nioSocketChannel.pipeline().addLast(new FirstServerChannel());
                    }
                })
                .bind(8000);
    }
}
