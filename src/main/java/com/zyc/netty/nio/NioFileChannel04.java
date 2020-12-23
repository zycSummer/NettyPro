package com.zyc.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 拷贝文件transferFrom方法 从目标通道中复制数据到当前通道
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/23 15:33
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NioFileChannel04 {
    public static void main(String[] args) throws Exception {
        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("d:\\jet\\file01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\jet\\file02.txt");

        // 获取各个流对应的fileChannel
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();

        // 使用fileOutputStream完成拷贝
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        // 关闭
        sourceChannel.close();
        destChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
