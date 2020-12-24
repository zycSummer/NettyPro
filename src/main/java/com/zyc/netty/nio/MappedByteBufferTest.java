package com.zyc.netty.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: MappedByteBuffer 可以让文件直接在内存(堆外内存)中修改,即操作系统不需要copy一次
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/24 9:55
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        // 获取对应的文件通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数一:FileChannel.MapMode.READ_WRITE 使用读写模式
         * 参数二:0 可以直接修改的起始位置
         * 参数三:5 是映射到内存的大小(不是索引),即将文件1.txt的多少个字节映射到内存
         * 可以直接修改的范围就是0-5
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) 9);
        mappedByteBuffer.put(5, (byte) 'Y');// IndexOutOfBoundsException
        randomAccessFile.close();
    }
}
