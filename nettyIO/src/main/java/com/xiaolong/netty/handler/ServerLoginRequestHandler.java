package com.xiaolong.netty.handler;

import com.xiaolong.netty.packet.Impl.LoginRequestPacket;
import com.xiaolong.netty.util.RequestProcessUtil;
import com.xiaolong.netty.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登录时保存会话信息，用户断开连接时删除会话信息
 */
@Slf4j
public class ServerLoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) {
        log.info("客户端发送了登录请求！");
        RequestProcessUtil.processLogin(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
