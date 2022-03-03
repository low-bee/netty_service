package com.xiaolong.netty;

import com.xiaolong.netty.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Test;
import org.junit.Before;

import java.util.Scanner;

public class OutputClient {

    Bootstrap bootstrap;
    NioEventLoopGroup group;

    @Before
    public void init(){
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
    }

    @Test
    public void testClientOutputData() throws InterruptedException {
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new FirstClientHandler());
                    }
                });
        NettyClientFailRetry.retryConnect(bootstrap, "127.0.0.1", 8006, 0);
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
    }

}
