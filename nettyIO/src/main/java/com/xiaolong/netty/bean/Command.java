package com.xiaolong.netty.bean;

public interface Command {

    /**
     * 登录客户端请求指令
     */
    Byte LOGIN_REQUEST = 1;
    /**
     * 登录客户端响应指令
     */
    Byte LOGIN_RESPONSE = 2;
    /**
     * 发送消息客户端请求指令
     */
    Byte MESSAGE_REQUEST = 3;
    /**
     * 发送消息客户端响应指令
     */
    Byte MESSAGE_RESPONSE = 4;
}
