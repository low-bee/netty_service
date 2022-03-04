package com.xiaolong.netty.packet;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.bean.Packet;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePacket extends Packet implements Command {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
