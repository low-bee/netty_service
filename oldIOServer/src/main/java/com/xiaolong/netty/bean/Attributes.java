package com.xiaolong.netty.bean;

import io.netty.util.AttributeKey;

/**
 * 定义登录成功标志位
 */
public interface Attributes {

    /**
     * 登录成功或失败
     * login -> true 表示成功，否则表示失败
     */
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

}
