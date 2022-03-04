package com.xiaolong.netty.util;

import cn.hutool.core.util.ObjectUtil;
import com.xiaolong.netty.bean.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {

    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        return ObjectUtil.isNotNull(attr);
    }
}
