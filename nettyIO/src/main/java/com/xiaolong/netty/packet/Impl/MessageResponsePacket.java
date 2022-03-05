package com.xiaolong.netty.packet.Impl;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.packet.Packet;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePacket extends Packet implements Command {

    private Integer fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
