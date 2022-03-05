package com.xiaolong.netty.handler;

import com.xiaolong.netty.packet.Impl.InLineQueryRequestPacket;
import com.xiaolong.netty.packet.Impl.InLineQueryResponsePacket;
import com.xiaolong.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 处理查询请求Handler
 * @Author xiaolong
 * @Date 2022/3/5 4:24 下午
 */
@Slf4j
public class ServerQueryInLineRequestHandler extends SimpleChannelInboundHandler<InLineQueryRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InLineQueryRequestPacket msg) throws Exception {
        log.info("执行了一次查询请求,id: {}", msg.getId());
        InLineQueryResponsePacket responsePacket = InLineQueryResponsePacket.builder()
                .userIds(LoginUtil.getLoginSuccessId().toString())
                .usernames(LoginUtil.getLoginSuccessUsername().toString())
                .build();

        ctx.channel().writeAndFlush(responsePacket);
    }
}
