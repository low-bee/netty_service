package com.xiaolong.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @Description: 第一个客户端channel
 * @Author xiaolong
 * @Date 2022/3/4 11:59 下午
 */
public class FirstClientChannel extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 1000; i++) {
            ByteBuf buf = getByteBuf(ctx);
            ctx.channel().writeAndFlush(buf);
            buf.release();
        }


    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] data = "你好，欢迎来到德玛西亚，我是盖伦。。。。".getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(data);
        return buffer;
    }
}
