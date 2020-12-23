package com.zyc.netty.nio;


import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 本地文件读数据
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/23 15:33
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NioFileChannel02 {
    public static void main(String[] args) throws Exception {
        // 创建一个输入流->channel
        File file = new File("d:\\jet\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 通过fileInputStream获取对应的fileChannel -> 实际类型 FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将通道的数据读入到Buffer
        fileChannel.read(byteBuffer); // 从通道读取数据并放到缓冲区中

        // 将byteBuffer的字节数据 转成字符串
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
