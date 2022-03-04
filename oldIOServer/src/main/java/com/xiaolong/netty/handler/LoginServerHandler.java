package com.xiaolong.netty.handler;

import com.xiaolong.netty.bean.Packet;
import com.xiaolong.netty.bean.UsernameAndPassword;
import com.xiaolong.netty.packet.LoginRequestPacket;
import com.xiaolong.netty.packet.LoginResponsePacket;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.bean.login.User;
import com.xiaolong.netty.packet.MessageRequestPacket;
import com.xiaolong.netty.packet.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到客户端发送信息！");
        ByteBuf currMsg = (ByteBuf) msg;
        log.debug(currMsg.toString());
        Packet decode = PacketCodeC.INSTANCE.decode(currMsg);
        if (decode instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) decode;
            ByteBuf result = processLogin(ctx, decode, loginRequestPacket);
            ctx.channel().writeAndFlush(result);
            log.info("写出完毕");
        } else if (decode instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) decode;

            log.info("收到客户消息：{}", messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .message("回复客户端消息 ["+messageRequestPacket.getMessage()+"]...")
                    .build();

            ByteBuf resultEncode = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(resultEncode);
        }


    }

    private ByteBuf processLogin(ChannelHandlerContext ctx, Packet decode, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket responsePacket  = new LoginResponsePacket();

        responsePacket.setVersion(decode.getVersion());
        if (UsernameAndPassword.valid(User
                .builder()
                .id(loginRequestPacket.getUserId())
                .username(loginRequestPacket.getUsername())
                .password(loginRequestPacket.getPassword())
                .build())) {
            // login success
            log.info("登录成功！");
            responsePacket.setSuccess(true);
        } else {
            // login fail
            log.info("登录失败！账号: {}，密码: {}", loginRequestPacket.getUsername(), loginRequestPacket.getPassword());
            responsePacket.setReason("账号或密码错误！");
            responsePacket.setSuccess(false);
        }

        return PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
    }

}
