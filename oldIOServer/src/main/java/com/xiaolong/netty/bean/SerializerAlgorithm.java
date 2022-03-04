package com.xiaolong.netty.bean;

import com.xiaolong.netty.bean.impl.JSONSerializer;

/**
 * 可选的序列化算法
 */
public interface SerializerAlgorithm {
    byte JSON = 1;

    /**
     * 默认采用JSON序列化对象
     */
    Serializer DEFAULT = new JSONSerializer();
}

