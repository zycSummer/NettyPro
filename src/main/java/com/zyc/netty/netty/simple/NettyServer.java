package com.zyc.netty.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/28 13:40
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        // 创建BossGroup 和 WorkerGroup
        // 说明
        // 1.创建2个线程组 bossGroup 和 workerGroup
        // 2.bossGroup只是处理连接请求,真正的和客户端业务处理，会交给 workerGroup 完成
        // 3.两个都是无线循环
        // 4.bossGroup 和 workerGroup 含有的子线程(nioEventLoop)的个数
        //      默认实际cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象,配置启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象(匿名对象)
                        // 给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给我们的 workerGroup 的 EventLoopGroup 对应的管道设置处理器
            System.out.println("服务器准备好了 is ready!!");

            // 绑定一个端口 并且同步,生成了一个 ChannelFuture 对象
            // 启动服务器(并绑定端口)
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
