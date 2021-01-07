package com.zyc.netty.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/7 13:22
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class GroupChatServer {

    private int port; // 监听端口

    public GroupChatServer(int port) {
        this.port = port;
    }

    // 编写run方法,处理客户端的请求
    public void run() throws Exception {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // 向pipeline里面加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 向pipeline里面加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 加入自己的业务处理handler
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("netty 服务器启动！");
            ChannelFuture future = b.bind(port).sync();
            // 监听关闭时间
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new GroupChatServer(7000).run();
    }
}
