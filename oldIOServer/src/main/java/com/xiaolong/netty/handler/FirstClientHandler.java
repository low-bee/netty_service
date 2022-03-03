package com.xiaolong.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据");

        // 获取数据
        ByteBuf buff = getByteBuf(ctx);
        // 通过socket写出
        ctx.channel().writeAndFlush(buff);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读取到数据 ->" + byteBuf.toString(StandardCharsets.UTF_8));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取二进制数据抽象
        ByteBuf buffer = ctx.alloc().buffer();
        // 准备数据，指定字符串的字符集
        byte[] bytes = "你好，闪电侠".getBytes(StandardCharsets.UTF_8);
        // 写到缓冲中
        buffer.writeBytes(bytes);

        return buffer;
    }
}
