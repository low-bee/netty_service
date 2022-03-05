package com.xiaolong.netty.client;

import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.handler.ClientLoginHandler;
import com.xiaolong.netty.packet.MessageRequestPacket;
import com.xiaolong.netty.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LoginClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup work = new NioEventLoopGroup();


        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientLoginHandler());
                    }
                });

        bootstrap.connect("127.0.0.1", 8009).addListener(future -> {
            if (future.isSuccess()) {
                Channel channel = ((ChannelFuture) future).channel();

                startConsoleThread(channel);
            }
        });


    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)){
                    System.out.print("输入消息发送至服务端：");
                    Scanner scanner = new Scanner(System.in);
                    String message = scanner.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(message);
                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }, "client thread").start();


    }
}
