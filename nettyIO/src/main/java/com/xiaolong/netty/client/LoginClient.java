package com.xiaolong.netty.client;

import com.xiaolong.netty.coder.PacketDecoder;
import com.xiaolong.netty.coder.PacketEncoder;
import com.xiaolong.netty.handler.ClientLoginResponseHandler;
import com.xiaolong.netty.handler.ClientMessageResponseHandler;
import com.xiaolong.netty.handler.ClientQueryInLineResponseHandler;
import com.xiaolong.netty.packet.Impl.InLineQueryRequestPacket;
import com.xiaolong.netty.packet.Impl.LoginRequestPacket;
import com.xiaolong.netty.packet.Impl.MessageRequestPacket;
import com.xiaolong.netty.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginClient {
    private static final Scanner scanner = new Scanner(System.in);
    private static Integer QUERY_NUM = 0;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        int id = 1;
        startClient(bootstrap, id);
    }

    public static void startClient(Bootstrap bootstrap, int id) {
        EventLoopGroup work = new NioEventLoopGroup();


        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new ClientMessageResponseHandler());
                        ch.pipeline().addLast(new ClientQueryInLineResponseHandler());
                        ch.pipeline().addLast(new ClientLoginResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });


        bootstrap.connect("127.0.0.1", 8000).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接成功！");
                Channel channel = ((ChannelFuture) future).channel();
                // 成功连接，开始进行消息发送
                startConsoleThread(channel, id);
            }
        });
    }

    private static void startConsoleThread(Channel channel, int id) throws InterruptedException {

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!LoginUtil.hasLogin(channel)){
                    System.out.println("输入账号密码登录：");
                    System.out.print("账号：");
                    String username = scanner.nextLine();
                    System.out.print("密码：");
                    String password = scanner.nextLine();

                    channel.writeAndFlush(LoginRequestPacket.builder()
                            .username(username)
                            .password(password)
                            .userId(id)
                            .build());
                    waitForLoginResponse();
                    channel.writeAndFlush(InLineQueryRequestPacket.builder().id(QUERY_NUM++).build());
                } else {
                    String userid = scanner.nextLine();
                    String message = scanner.nextLine();

                    channel.writeAndFlush(InLineQueryRequestPacket.builder().id(QUERY_NUM++).build());
                    channel.writeAndFlush(MessageRequestPacket.builder().toUserId(Integer.parseInt(userid)).message(message).build());
                }

            }
        }, "client thread").start();


    }

    private static void waitForLoginResponse() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("线程异常中断{}", Thread.currentThread().getName());
            e.printStackTrace();
        }
    }
}
