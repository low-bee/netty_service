package com.xiaolong.netty.handler;

import com.xiaolong.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: 登录消息验证器
 * @Author xiaolong
 * @Date 2022/3/5 9:20 上午
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtil.hasLogin(ctx.channel())){
            // 未登录直接关闭当前连接
            ctx.channel().close();
            return;
        }
        // 否则直接通过, 并且移除后续验证逻辑
        ctx.pipeline().remove(this);
        super.channelRead(ctx, msg);
    }
}
