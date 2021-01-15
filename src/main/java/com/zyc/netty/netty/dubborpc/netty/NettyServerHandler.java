package com.zyc.netty.netty.dubborpc.netty;

import com.zyc.netty.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/15 14:03
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息,并调用我们的服务
        System.out.println("msg= " + msg);
        // 客户端在调用服务器的api时,我们需要定义一个协议
        // 比如我们要求 每次发消息时 都必须以某个字符串开头 "helloService#hello#"
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
