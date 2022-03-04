package com.xiaolong.netty.packet;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.bean.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequestPacket extends Packet implements Command {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
