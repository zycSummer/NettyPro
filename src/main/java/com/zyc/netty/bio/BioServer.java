package com.zyc.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: zyc
 * Date: 2020/9/29
 * Time: 21:42
 * Description: 首先启动创建主线程进行监听，有客户端连接创建子线程，主线程继续监听，以此类推
 */
public class BioServer {
    public static void main(String[] args) throws Exception {
        // 线程池机制

        // 思路
        // 1.创建一个线程池
        // 2.如果有客户端连接，就创建一个线程，与之通讯(单独写一个方法)

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            // 监听，等待客户端连接
            final Socket socket = serverSocket.accept(); // 会阻塞
            System.out.println("连接到一个客户端");

            // 创建一个线程，与之通讯(单独写一个方法)
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    // 重写 可以和客户端通讯
                    handler(socket);
                }
            });
        }
    }

    // 编写一个handler方法和客户端通讯
    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        // 通过socket获取输入流
        try {
            System.out.println("线程信息 id=" + Thread.currentThread().getId() + "name = " + Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (true) {
                System.out.println("读取线程信息 id=" + Thread.currentThread().getId() + "name = " + Thread.currentThread().getName());
                int read = inputStream.read(bytes);// 会阻塞
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));// 输出客户端发送的数据
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
