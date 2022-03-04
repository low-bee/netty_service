package com.xiaolong.netty.handler;

import com.xiaolong.netty.packet.MessageRequestPacket;
import com.xiaolong.netty.util.RequestProcessUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    /**
     * Netty会做判断，只有packet是MessageRequestHandler才会执行channelRead0方法
     * @param ctx 上下文对象
     * @param msg 消息对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        ByteBuf byteBuf = RequestProcessUtil.messageProcess(ctx, msg);
        ctx.channel().writeAndFlush(byteBuf);
    }


}
