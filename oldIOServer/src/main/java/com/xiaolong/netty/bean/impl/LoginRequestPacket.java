package com.xiaolong.netty.bean.impl;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.bean.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet implements Command {

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;

    }
}
