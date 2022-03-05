package com.xiaolong.netty.packet.Impl;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.packet.Packet;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestPacket extends Packet implements Command {

    /**
     * 需要发送非某个用户的数据表标志
     */
    private Integer toUserId;

    /**
     * 发送出去的数据包信息
     */
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
