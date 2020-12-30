package com.zyc.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/30 10:32
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器
        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个netty提供的httpServerCodec
        // HttpServerCodec 说明
        // 1.HttpServerCodec 是netty提供的处理http的编-解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        // 2.增加一个自定义的handler
        pipeline.addLast("MyHttpServerHandler",new TestHttpServerHandler());
    }
}
