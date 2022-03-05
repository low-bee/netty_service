package com.xiaolong.netty.util;

import cn.hutool.core.util.ObjectUtil;
import com.xiaolong.netty.bean.Attributes;
import com.xiaolong.netty.bean.Session;
import com.xiaolong.netty.bean.UsernameAndPassword;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.entry.User;
import com.xiaolong.netty.packet.Impl.LoginRequestPacket;
import com.xiaolong.netty.packet.Impl.LoginResponsePacket;
import com.xiaolong.netty.packet.Impl.MessageRequestPacket;
import com.xiaolong.netty.packet.Impl.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RequestProcessUtil {

    static Random random = new Random();

    /**
     * 将消息发送给接收方, 服务器的作用相当于是转发
     * @param ctx 当前连接的上下文对象
     * @param messageRequestPacket 发送过来的消息包
     */
    public static void messageProcess(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        // 获取消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());
        if (session == null){
            log.info("session 为空！");
            return;
        }
        // 通过会话消息将消息构造出来
        System.out.println(session.getUsername());

        MessageResponsePacket message = MessageResponsePacket.builder()
                .message(messageRequestPacket.getMessage())
                .fromUserName(session.getUsername())
                .fromUserId(session.getUserId())
                .build();

        // 获取消息接受方的channel
        Channel inputChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 将消息写出去
        if (ObjectUtil.isNotNull(inputChannel) && SessionUtil.hasLogin(inputChannel)){
            inputChannel.writeAndFlush(message);
        } else {
            log.error("[ {} ]， 不在线，发送失败！", messageRequestPacket.getToUserId());
        }
    }

    public static void processLogin(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket responsePacket  = new LoginResponsePacket();
        // 设置用户响应版本
        responsePacket.setVersion(loginRequestPacket.getVersion());
        // 设置用户名id
        // generate id
        Integer userId = getUserId(loginRequestPacket);

        responsePacket.setUserId(userId);
        SessionUtil.bindSession(Session.builder().userId(userId).username(loginRequestPacket.getUsername()).build(), ctx.channel());
        if (UsernameAndPassword.valid(User
                .builder()
                .id(loginRequestPacket.getUserId())
                .username(loginRequestPacket.getUsername())
                .password(loginRequestPacket.getPassword())
                .build())) {

            // login success
            LoginUtil.markAsLogin(ctx.channel());
            // 登录成功添加到对应的存储中
            LoginUtil.loginSuccess(loginRequestPacket.getUserId(), loginRequestPacket.getUsername());
            log.info("登录成功！");
            responsePacket.setSuccess(true);
        } else {
            // login fail
            log.info("登录失败！账号: {}，密码: {}", loginRequestPacket.getUsername(), loginRequestPacket.getPassword());
            responsePacket.setReason("账号或密码错误！");
            responsePacket.setSuccess(false);
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    /**
     * 生成当前的用户id
     * @return 用户ID
     */
    private static Integer getUserId(LoginRequestPacket loginRequestPacket) {
        return loginRequestPacket.getUserId();
    }

    public static ByteBuf messageProcess(ChannelHandlerContext ctx, MessageResponsePacket messagerequestpacket) {
        MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                .message("回复客户端消息 ["+ messagerequestpacket.getMessage()+"]...")
                .build();
        return PacketCodeC.INSTANCE.encode(messageResponsePacket);
    }
}
