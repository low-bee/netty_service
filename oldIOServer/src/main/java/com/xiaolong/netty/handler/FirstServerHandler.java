package com.xiaolong.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String s = byteBuf.toString(StandardCharsets.UTF_8);
        System.out.println(new Date() + ": 服务器读取到数据 -> " + s);

        System.out.println(new Date() + "： 服务器写出数据");
        ctx.channel().writeAndFlush(getBuffer(ctx, s));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf ab = getBuffer(ctx, "成功建立连接！");
        ctx.channel().writeAndFlush(ab);
    }

    private ByteBuf getBuffer(ChannelHandlerContext ctx, String s) {
        String[] s2 = s.split(" ");
        String s1 = "hello " + (s2.length > 0 ? s2[0] : null);
        byte[] bytes = s1.getBytes(StandardCharsets.UTF_8);

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
