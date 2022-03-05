package com.xiaolong.netty.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 为Attr绑定的属性
 * @Author xiaolong
 * @Date 2022/3/5 10:42 上午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

}
