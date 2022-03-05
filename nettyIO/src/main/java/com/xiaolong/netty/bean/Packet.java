package com.xiaolong.netty.bean;

import lombok.Data;

@Data
public abstract class Packet{
    /**
     * Byte类型的版本
     */
    private Byte version = 1;

    /**
     * 通信的指令
     * @return 一个特定的指令
     */
    public abstract Byte getCommand();
}
