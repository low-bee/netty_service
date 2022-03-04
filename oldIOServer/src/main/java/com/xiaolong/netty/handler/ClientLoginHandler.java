package com.xiaolong.netty.handler;

import com.xiaolong.netty.bean.Packet;
import com.xiaolong.netty.packet.LoginRequestPacket;
import com.xiaolong.netty.packet.LoginResponsePacket;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.packet.MessageResponsePacket;
import com.xiaolong.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientLoginHandler extends ChannelInboundHandlerAdapter {

    private LoginRequestPacket packet;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info( "客户端开始登录");
        // 创建登录对象
        packet = LoginRequestPacket.getLoginPacket(1, "test", "test");

        // 编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet);

        ctx.channel().writeAndFlush(buffer);
        log.info("客户端发送账号密码.....");
        log.info("等待.....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("接收到服务端消息！");
        ByteBuf currMsg = (ByteBuf) msg;

        Packet decode = PacketCodeC.INSTANCE.decode(currMsg);

        if (decode instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) decode;

            if (loginResponsePacket.isSuccess()){
                LoginUtil.markAsLogin(ctx.channel());
                log.info("用户{}登录成功", packet.getUsername());
            } else {
                log.info("用户{}登录失败，原因: {}", packet.getUsername(), loginResponsePacket.getReason());

            }
        } else if (decode instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) decode;
            if (messageResponsePacket.getMessage() != null) {
                log.info("收到服务端消息：{}", messageResponsePacket.getMessage());
            }
        }
    }
}
