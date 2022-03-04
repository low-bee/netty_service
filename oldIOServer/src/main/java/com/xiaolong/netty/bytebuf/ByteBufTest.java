package com.xiaolong.netty.bytebuf;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.Test;

@Slf4j
public class ByteBufTest {

    @Test
    public void testByteBufTest() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        log.info("分配buffer(9, 100), {}", buffer);

        // write 方法改变写指针，在指针未到capacity时，指针任可写
        buffer.writeBytes(new byte[]{1,2,3,4});
        log.info("writer(byte[1,2,3,4]) {}", buffer);

        int i = buffer.readInt();

        System.out.println(i);

        buffer.writeInt(12);
        log.info("写出一个整数, {}", buffer);

        buffer.writeBytes(new byte[]{5});
        log.info("写出一个整数, {}", buffer);

    }

    public static void main(String[] args) {
    }

    public void test() {
        ;
    }

}
