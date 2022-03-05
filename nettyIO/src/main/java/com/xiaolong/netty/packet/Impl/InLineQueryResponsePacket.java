package com.xiaolong.netty.packet.Impl;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.packet.Packet;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @Description: 查询是否在线接口响应
 * @Author xiaolong
 * @Date 2022/3/5 4:15 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InLineQueryResponsePacket extends Packet implements Command {

    private String userIds;

    private String usernames;

    @Override
    public Byte getCommand() {
        return RESPONSE_IN_LINE;
    }
}
