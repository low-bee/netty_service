package com.xiaolong.netty.util;

import com.xiaolong.netty.bean.UsernameAndPassword;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.bean.login.User;
import com.xiaolong.netty.packet.LoginRequestPacket;
import com.xiaolong.netty.packet.LoginResponsePacket;
import com.xiaolong.netty.packet.MessageRequestPacket;
import com.xiaolong.netty.packet.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestProcessUtil {

    public static ByteBuf messageProcess(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                .message("回复客户端消息 ["+ messageRequestPacket.getMessage()+"]...")
                .build();
        return PacketCodeC.INSTANCE.encode(messageResponsePacket);
    }

    public static ByteBuf processLogin(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket responsePacket  = new LoginResponsePacket();
        responsePacket.setVersion(loginRequestPacket.getVersion());
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

    public static ByteBuf messageProcess(ChannelHandlerContext ctx, MessageResponsePacket messagerequestpacket) {
        MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                .message("回复客户端消息 ["+ messagerequestpacket.getMessage()+"]...")
                .build();
        return PacketCodeC.INSTANCE.encode(messageResponsePacket);
    }
}
