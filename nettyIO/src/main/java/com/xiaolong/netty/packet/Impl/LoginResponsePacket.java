package com.xiaolong.netty.packet.Impl;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends Packet implements Command {

    /**
     * 响应是否成功
     */
    private boolean success;

    /**
     * 响应失败原因
     */
    private String reason;

    /**
     * 登录时绑定的用户id
     */
    private Integer userId;
    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
