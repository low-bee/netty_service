package com.xiaolong.netty.bean;


import com.xiaolong.netty.entry.User;

import java.util.HashSet;

public class UsernameAndPassword {
    static HashSet<User> users;
    static {
        users = new HashSet<>();
        users.add(User.builder().id(0).username("test").password("test").build());
    }
    public static boolean valid(User user){
        return users.contains(user) ;
    }
}
