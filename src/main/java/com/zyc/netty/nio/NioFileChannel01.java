package com.zyc.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 本地文件写数据 (hello, zyc先写入byteBuffer，然后将缓冲区byteBuffer写入到fileChannel里面，最后通过java输出流对象 写入文件中)
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/23 15:33
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NioFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "hello,zyc";
        // 创建一个输出流->channel
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\jet\\file01.txt");

        // 通过fileOutputStream获取对应的FileChannel
        // 这个fileChannel真实类型FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将str放入到byteBuffer
        byteBuffer.put(str.getBytes());

        // 对byteBuffer进行反转
        byteBuffer.flip();

        // 将byteBuffer数据写入到fileChannel
        fileChannel.write(byteBuffer); // 从缓冲区的数据写到通道中

        fileOutputStream.close();
    }
}
