package com.xiaolong.netty.handler;

import com.xiaolong.netty.bean.Packet;
import com.xiaolong.netty.bean.UsernameAndPassword;
import com.xiaolong.netty.packet.LoginRequestPacket;
import com.xiaolong.netty.packet.LoginResponsePacket;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.bean.login.User;
import com.xiaolong.netty.packet.MessageRequestPacket;
import com.xiaolong.netty.packet.MessageResponsePacket;
import com.xiaolong.netty.util.RequestProcessUtil;
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
        Packet decode = PacketCodeC.INSTANCE.decode(currMsg);
        if (decode instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) decode;
            ByteBuf result = RequestProcessUtil.processLogin(ctx, loginRequestPacket);
            ctx.channel().writeAndFlush(result);
            log.debug("写出完毕");
        } else if (decode instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) decode;

            log.debug("收到客户消息：{}", messageRequestPacket.getMessage());
            ByteBuf resultEncode = RequestProcessUtil.messageProcess(ctx, messageRequestPacket);
            ctx.channel().writeAndFlush(resultEncode);
        }


    }


}
