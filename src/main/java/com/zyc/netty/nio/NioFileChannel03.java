package com.zyc.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 使用一个Buffer完成文件读取
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/23 15:33
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NioFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {//循环读取
            // 这里有一个重要的操作，不要忘了
            /**
             *    public final Buffer clear() {
             *         position = 0;
             *         limit = capacity;
             *         mark = -1;
             *         return this;
             *     }
             */
            byteBuffer.clear();// 清空buffer
            int read = fileChannel01.read(byteBuffer);// 从通道读取数据并放到缓冲区中
            System.out.println("read:" + read);
            if (read == -1) { // 表示读完
                break;
            }
            // 将buffer中的数据写入到fileChannel02 ->2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);// 从缓冲区的数据写到通道中
        }

        // 关闭
        fileInputStream.close();
        fileOutputStream.close();
    }
}
