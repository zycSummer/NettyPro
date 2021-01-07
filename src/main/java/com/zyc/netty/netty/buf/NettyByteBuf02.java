package com.zyc.netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/7 10:44
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        // 创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", StandardCharsets.UTF_8);
        // 使用相关的方法
        if (byteBuf.hasArray()) { // true
            byte[] content = byteBuf.array();
            // 将content 转成字符串
            System.out.println(new String(content, StandardCharsets.UTF_8));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset());// 0
            System.out.println(byteBuf.readerIndex());// 0
            System.out.println(byteBuf.writerIndex());// 18
            System.out.println(byteBuf.capacity());// 42

//            System.out.println(byteBuf.readByte());// h的ASCII码是104,会导致readerIndex变化
            System.out.println(byteBuf.getByte(0));// h的ASCII码是104,不会导致readerIndex变化

            int len = byteBuf.readableBytes();// 可读的字节数 18
            System.out.println("len=" + len);

            // 使用for取出各个字符
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0, 4, StandardCharsets.UTF_8));
            System.out.println(byteBuf.getCharSequence(4, 6, StandardCharsets.UTF_8));
        }
    }
}
