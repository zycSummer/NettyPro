package com.zyc.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/24 13:59
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NioServer {
    public static void main(String[] args) throws Exception {
        // 创建ServerSocketChannel 类似ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个selector对象
        Selector selector = Selector.open();

        // 绑定一个端口6666,在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel 注册到 selector 关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            // 这里我们等待一秒,如果没有事件发生,返回
            if (selector.select(1000) == 0) { // 没有事件发生
                System.out.println("服务器等待了一秒,无连接");
                continue;
            }
            // 如果返回的>0,就获取到相关的SelectionKey集合
            // 1.如果返回的>0,表示已经获取到关注的实践
            // 2.selector.selectedKeys()返回关注事件的集合
            //   通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                // 获取到SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据key 对应的通道发生的事件做相应的处理
                if (key.isAcceptable()) { // 如果是OP_ACCEPT,有新的客户端连接我
                    // 给该客户端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功,生成了一个socketChannel " + socketChannel.hashCode());
                    // 将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将当前的socketChannel 注册到 selector上,关注事件OP_READ,同时给socketChannel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) { // 发送OP_READ
                    // 通过反向获取到对应的channel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("from 客户端 " + new String(byteBuffer.array()));
                }
                // 手动从集合中移除当前的SelectionKey,防止重复操作
                keyIterator.remove();
            }
        }
    }
}
