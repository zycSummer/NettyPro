package com.zyc.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/24 14:27
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();

        // 设置非阻塞
        socketChannel.configureBlocking(false);

        // 提供服务器端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间,客户端不会阻塞,可以做其他工作。。。");
            }
        }

        // 如果连接成功就发送数据
        String str = "hello nio~";
        // Wraps a byte array into a buffer.
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据,将buffer数据写入channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
