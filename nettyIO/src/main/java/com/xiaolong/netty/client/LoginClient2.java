package com.xiaolong.netty.client;

import io.netty.bootstrap.Bootstrap;

/**
 * @Description: 登录客户端2
 * @Author xiaolong
 * @Date 2022/3/5 9:07 下午
 */
public class LoginClient2 {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        LoginClient.startClient(bootstrap,3);
    }
}
