package com.xiaolong.netty.handler;

import com.xiaolong.netty.packet.Impl.InLineQueryResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 客户端的查询请求
 * @Author xiaolong
 * @Date 2022/3/5 8:51 下午
 */
@Slf4j
public class ClientQueryInLineResponseHandler extends SimpleChannelInboundHandler<InLineQueryResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InLineQueryResponsePacket msg) throws Exception {
        System.out.println(msg.getUsernames());
        System.out.println(msg.getUserIds());
    }
}
