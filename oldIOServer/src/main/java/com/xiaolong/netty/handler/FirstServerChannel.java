package com.xiaolong.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @Description: 测试粘包和拆包
 * @Author xiaolong
 * @Date 2022/3/4 11:50 下午
 */
@Slf4j
public class FirstServerChannel extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf curr = (ByteBuf) msg;
        log.info("服务端读取到数据: {}", curr.toString(StandardCharsets.UTF_8));
    }
}
