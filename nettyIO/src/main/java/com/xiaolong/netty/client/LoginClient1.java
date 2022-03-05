package com.xiaolong.netty.client;

import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.coder.PacketDecoder;
import com.xiaolong.netty.coder.PacketEncoder;
import com.xiaolong.netty.handler.ClientLoginResponseHandler;
import com.xiaolong.netty.handler.ClientMessageResponseHandler;
import com.xiaolong.netty.handler.ClientQueryInLineResponseHandler;
import com.xiaolong.netty.packet.Impl.LoginRequestPacket;
import com.xiaolong.netty.packet.Impl.MessageRequestPacket;
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

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginClient1 {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        LoginClient.startClient(bootstrap,2);
    }
}
