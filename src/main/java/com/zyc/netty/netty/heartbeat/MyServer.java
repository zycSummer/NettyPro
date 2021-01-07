package com.zyc.netty.netty.heartbeat;

import com.zyc.netty.netty.groupchat.GroupChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/7 15:44
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 在bossGroup 增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 加入一个netty 提供 IdleStateHandler
                            /**
                             * 1.IdleStateHandler是 netty 提供的处理空闲状态的处理器
                             * 2.long readerIdleTime: 表示多长时间没有读,就会发送一个心跳检测包检测是否连接
                             * 3.long writerIdleTime: 表示多长时间没有写,就会发送一个心跳检测包检测是否连接
                             * 4.long allIdleTime: 表示多长时间没有读写,就会发送一个心跳检测包检测是否连接
                             * 5.当IdleStateHandler 触发后就会传递给管道的下一个handler去处理,通过调用(触发)下一个 handler 的 userEventTriggered,
                             *      在该方法中去处理IdleStateHandler(读空闲，写空闲，读写空闲)
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的handler(自定义)
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            System.out.println("netty 服务器启动！");
            ChannelFuture future = b.bind(7000).sync();
            // 监听关闭时间
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
