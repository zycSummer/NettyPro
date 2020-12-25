package com.zyc.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/25 9:41
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class GroupChatServer {
    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 构造器
    // 初始化工作
    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞
            listenChannel.configureBlocking(false);
            // 将该listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        try {
            // 循环处理
            while (true) {
                int count = selector.select();
                if (count > 0) { // 有事件处理
                    // 遍历得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        // 取除SelectionKey
                        SelectionKey key = iterator.next();
                        // 监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            // 将该sc注册到 selector
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            // 将socketChannel设置为非阻塞
                            // 提示
                            System.out.println(sc.getRemoteAddress() + " 上线啦！！！");
                        }
                        if (key.isReadable()) { // 通道发生read事件,即通道是刻度的状态
                            // 处理读
                            readData(key);
                        }
                        // 当然的key删除 防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //发生异常处理....
        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key) {
        // 定义一个SocketChannel
        SocketChannel channel = null;
        try {
            // 取到关联的channel
            channel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);// 从channel读取数据到buffer
            // 根据count的值做处理
            if (count > 0) {
                // 把缓冲区的数据转成字符串并输出
                String msg = new String(byteBuffer.array());
                // 输出该消息
                System.out.println("from 客户端: " + msg);
                // 向其他客户端(去掉自己)转发消息,专门写个方法处理
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了！！！");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // 转发消息给其它客户端(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        // 遍历 所有的注册到selector上的SocketChannel 并排除self
        for (SelectionKey key : selector.keys()) {
            // 通过key 取出对应的 SocketChannel
            Channel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 将buffer的数据写入通道中
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
