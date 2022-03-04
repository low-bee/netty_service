package com.xiaolong.netty.handler;

import com.xiaolong.netty.bean.impl.LoginRequestPacket;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class ClientLoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info( "客户端开始登录");
        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(1);
        loginRequestPacket.setUsername("test");
        loginRequestPacket.setPassword("test");

        // 编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);
        log.info("客户端发送账号密码.....");
        log.info("等待.....");
    }
}
