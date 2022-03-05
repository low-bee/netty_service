package com.xiaolong.netty;

import com.xiaolong.netty.handler.FirstClientChannel;
import com.xiaolong.netty.handler.FirstServerChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description: 第一个客户端
 * @Author xiaolong
 * @Date 2022/3/4 11:58 下午
 */
public class FirstClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new FirstClientChannel());
                    }
                });

        bootstrap.connect("127.0.0.1", 8000);
    }
}
