package com.xiaolong.netty.packet.Impl;

import com.xiaolong.netty.bean.Command;
import com.xiaolong.netty.packet.Packet;
import lombok.*;

/**
 * @Description: 查询请求指令
 * @Author xiaolong
 * @Date 2022/3/5 4:19 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InLineQueryRequestPacket extends Packet implements Command {
    private Integer id;
    @Override
    public Byte getCommand() {
        return QUERY_IN_LINE;
    }
}
