package com.xiaolong.netty.bean;

import com.xiaolong.netty.bean.impl.JSONSerializer;

/**
 * 自定义的序列化器
 */
public interface Serializer {
    JSONSerializer DEFAULT = new JSONSerializer();

    /**
     * 获取序列化算法
     * @return 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * 将对象进行序列化
     * @param obj 待序列化对象
     * @return 序列化后的对象
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化器
     * @param clazz 需要将二进制数据序列化为什么数据
     * @param bytes 待序列化字节数据
     * @param <T> 序列化泛型
     * @return 序列化后的T类型对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
