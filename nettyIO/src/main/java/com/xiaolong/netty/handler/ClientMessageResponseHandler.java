package com.xiaolong.netty.handler;

import com.xiaolong.netty.packet.Impl.LoginResponsePacket;
import com.xiaolong.netty.packet.Impl.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 客户端响应服务器消息处理器,处理MessageResponsePacket
 * @Author xiaolong
 * @Date 2022/3/5 11:40 上午
 */
@Slf4j
public class ClientMessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        Integer fromUserId = msg.getFromUserId();
        String fromUserName = msg.getFromUserName();

        log.info("{}: {} -> {}", fromUserId, fromUserName, msg.getMessage());
    }
}
