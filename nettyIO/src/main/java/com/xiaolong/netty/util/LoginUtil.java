package com.xiaolong.netty.util;

import com.xiaolong.netty.bean.Attributes;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashSet;

@Data
public class LoginUtil {

    private static HashSet<Integer> loginSuccessId = new HashSet<>();
    private static HashSet<String>  loginSuccessUsername = new HashSet<>();

    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.LOGIN);
    }

    public static void loginSuccess(Integer userId, String username) {
        loginSuccessId.add(userId);
        loginSuccessUsername.add(username);
    }

    public static void loginOut(Integer userId, String username){
        loginSuccessId.remove(userId);
        loginSuccessUsername.remove(username);
    }

    public static HashSet<Integer> getLoginSuccessId() {
        return loginSuccessId;
    }

    public static HashSet<String> getLoginSuccessUsername() {
        return loginSuccessUsername;
    }
}
