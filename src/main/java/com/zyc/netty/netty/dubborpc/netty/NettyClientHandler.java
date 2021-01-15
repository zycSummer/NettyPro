package com.zyc.netty.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/15 14:18
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext context;// 上下文
    private String result;// 返回的结果
    private String para;// 客户端调用方法时,传入的参数

    // 与服务器的连接创建后,就会被调用(第一个被调用)
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx; // 因为我在其他方法会使用到这个ctx
    }

    // 第四个调用
    // 收到服务器的数据后,调用方法
    // notify 唤醒等待的线程
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    // 第三个调用->第四个调用完然后再回来
    // 被代理对象调动,发送数据给服务器,wait->等待被唤醒(channelRead)->返回结果
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        // 进行wait
        wait();// 等待 channelRead方法 获取到服务器的结果后,被唤醒
        return result;// 服务方返回的结果
    }

    // 第二个调用
    public void setPara(String para) {
        this.para = para;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
