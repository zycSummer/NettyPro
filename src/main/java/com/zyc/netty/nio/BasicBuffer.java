package com.zyc.netty.nio;

import java.nio.IntBuffer;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/23 13:46
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 举例说明buffer的使用
        // 创建一个Buffer,大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 如何从buffer读取数据
        // 将buffer转换,读写切换(!!!)
        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(3);
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
