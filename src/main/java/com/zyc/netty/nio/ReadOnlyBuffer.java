package com.zyc.netty.nio;

import java.nio.ByteBuffer;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 只读的buffer
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/24 9:49
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        // 创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        // 读取
        buffer.flip();

        // 得到一个只读的buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        // 读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        readOnlyBuffer.put((byte) 100);// ReadOnlyBufferException
    }
}
