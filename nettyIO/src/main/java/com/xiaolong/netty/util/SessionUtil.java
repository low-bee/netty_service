package com.xiaolong.netty.util;

import com.xiaolong.netty.bean.Attributes;
import com.xiaolong.netty.bean.Session;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 保存user -> channel 的映射
 * @Author xiaolong
 * @Date 2022/3/5 10:38 上午
 */

public class SessionUtil {

    /**
     * userid -> Channel 的映射
     */
    private static final Map<Integer, Channel> userIdChannel = new ConcurrentHashMap<>();

    /**
     * 在每一个连接channel中绑定Session
     * @param session 待绑定的session
     * @param channel 待被绑定的channel
     */
    public static void bindSession(Session session, Channel channel){
        channel.attr(Attributes.SESSION).set(session);
        userIdChannel.put(session.getUserId(), channel);
    }

    /**
     * 从channel将绑定的Session删除
     * @param channel 需要删除Session的channel
     */
    public static void unBindSession(Channel channel){
        if (hasLogin(channel)){
            userIdChannel.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 获取当前channel所绑定的channel
     * @param channel 当前连接的 channel
     * @return 当前Channel绑定的Session
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Channel getChannel(Integer userId){

        System.out.println(userIdChannel.get(userId));
        return userIdChannel.get(userId);
    }



}
