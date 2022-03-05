package com.xiaolong.netty.server;

import com.xiaolong.netty.coder.PacketDecoder;
import com.xiaolong.netty.coder.PacketEncoder;
import com.xiaolong.netty.handler.AuthHandler;
import com.xiaolong.netty.handler.ServerLoginRequestHandler;
import com.xiaolong.netty.handler.ServerMessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LoginServer {


    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        bootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new ServerLoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new ServerMessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                    }
                });
        bootstrap.bind(8000);

    }
}
