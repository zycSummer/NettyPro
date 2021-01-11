package com.zyc.netty.netty.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/11 14:26
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 入站的handler进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());
        // 出站的handler进行编码 MyLongToByteEncoder
        pipeline.addLast(new MyLongToByteEncoder());
        // 自定义的handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
