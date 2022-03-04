package com.xiaolong.netty.handler;

import com.xiaolong.netty.bean.Packet;
import com.xiaolong.netty.bean.UsernameAndPassword;
import com.xiaolong.netty.bean.impl.LoginRequestPacket;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.bean.login.User;
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

            if (UsernameAndPassword.valid(User
                    .builder()
                    .id(loginRequestPacket.getUserId())
                    .username(loginRequestPacket.getUsername())
                    .password(loginRequestPacket.getPassword())
                    .build())) {
                // login success
                log.info("登录成功！");
            } else {
                // login fail
                log.info("登录失败！");
            }
        }
    }

}
