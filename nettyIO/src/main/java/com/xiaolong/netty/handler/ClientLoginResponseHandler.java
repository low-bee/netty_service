package com.xiaolong.netty.handler;

import com.xiaolong.netty.packet.Impl.LoginResponsePacket;
import com.xiaolong.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientLoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        if (msg.isSuccess()){
            LoginUtil.markAsLogin(ctx.channel());
            log.info("用户登录成功");
        } else {
            log.info("用户登录失败");
        }
    }
}
