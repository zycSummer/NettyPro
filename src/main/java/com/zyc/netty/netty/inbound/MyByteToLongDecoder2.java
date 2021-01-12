package com.zyc.netty.netty.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: decode 会根据接收到的数据,被调用多次,直到确定没有新的元素被添加到list,或者是ByteBuf 没有更多的可读字节为止
 * 如果list out 不为空,就会将list的内容传递给下一个 ChannelInboundHandler 处理,该处理器的方法也会被调用多次
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/11 14:28
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    /**
     * @param ctx 上下文对象
     * @param in  入站的 ByteBuf
     * @param out List 集合,将解码后的数据传给下一个handler处理
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 decode被调用！");
        // 在 ReplayingDecoder 不需要判断数据是否足够读取，内部会进行处理判断
        out.add(in.readLong());
    }
}
