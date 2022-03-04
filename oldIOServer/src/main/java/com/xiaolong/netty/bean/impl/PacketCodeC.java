package com.xiaolong.netty.bean.impl;

import com.xiaolong.netty.bean.Packet;
import com.xiaolong.netty.bean.Serializer;
import com.xiaolong.netty.bean.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    /**
     * 标识当前协议的魔术
     */
    private static final int MAGIC_NUMBER = 0x12345678;

    /**
     * 将对象进行序列化写出
     *  魔数 int
     *  版本 byte
     *  序列化算法 byte
     *  指令 byte
     *  数据包 bytes
     * @param packet 待序列化的packet
     * @return 一个ByteBuf，封装有带有特定协议的对象
     */
    public ByteBuf encode(Packet packet) {
        //1. 创建ByteBuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        return encode(byteBuf, packet);
    }

    /**
     * 将对象进行序列化写出
     *  魔数 int
     *  版本 byte
     *  序列化算法 byte
     *  指令 byte
     *  数据包 bytes
     * @param packet 待序列化的packet
     * @param allocator 分配器
     * @return 一个ByteBuf，封装有带有特定协议的对象
     */
    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        ByteBuf buffer = encode(allocator, packet, 1024, 2048);
        return encode(buffer, packet);
    }

    /**
     * 将对象进行序列化写出
     *  魔数 int
     *  版本 byte
     *  序列化算法 byte
     *  指令 byte
     *  数据包 bytes
     * @param packet 待序列化的packet
     * @param allocator 分配器
     * @return 一个ByteBuf，封装有带有特定协议的对象
     */
    public ByteBuf encode(ByteBufAllocator allocator, Packet packet, int initCapacity, int maxCapacity) {
        ByteBuf buffer = allocator.buffer(initCapacity, maxCapacity);
        return encode(buffer, packet);
    }

    /**
     * 将对象进行序列化写出
     *  魔数 int
     *  版本 byte
     *  序列化算法 byte
     *  指令 byte
     *  数据包 bytes
     * @param packet 待序列化的packet
     * @param byteBuf 分配的内存
     * @return 一个ByteBuf，封装有带有特定协议的对象
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        //2. 序列化packet对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 开始编码
        // 写出魔数 4
        byteBuf.writeInt(MAGIC_NUMBER);
        // 写出版本 1
        byteBuf.writeByte(packet.getVersion());
        // 写出序列化算法 1
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        // 写出指令 1
        byteBuf.writeByte(packet.getCommand());
        // 长度 4
        byteBuf.writeInt(bytes.length);
        // 写出对象
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    /**
     * 使用特定的协议对数据解码
     * 1. 魔数
     * 2. 版本
     * 3. 指定的序列化算法
     * 4. 指定的指令方式
     * 5. 读取数据包长度
     * 6. 反序列化
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf){
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 版本
        byteBuf.skipBytes(1);
        // 读一个字节的算法
        byte serializerAlgorithm = byteBuf.readByte();
        // 读一个字节的指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int dataLength = byteBuf.readInt();

        byte[] bytes = new byte[dataLength];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);
        Packet deserialize = null;
        if (requestType != null && serializer != null){
            deserialize = serializer.deserialize(requestType, bytes);
        }

        return deserialize;

    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        if (serializerAlgorithm == SerializerAlgorithm.JSON){
            return Serializer.DEFAULT;
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if (LoginRequestPacket.LOGIN_REQUEST.equals(command)){
            return LoginRequestPacket.class;
        }
        return null;
    }
}
