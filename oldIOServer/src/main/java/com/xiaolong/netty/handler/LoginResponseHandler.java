package com.xiaolong.netty.handler;

import com.xiaolong.netty.packet.LoginRequestPacket;
import com.xiaolong.netty.util.RequestProcessUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        ByteBuf byteBuf = RequestProcessUtil.processLogin(ctx, msg);
        ctx.channel().writeAndFlush(byteBuf);
    }

}
