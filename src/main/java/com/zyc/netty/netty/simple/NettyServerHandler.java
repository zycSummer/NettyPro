package com.zyc.netty.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 1.我们自定义一个Handler需要继承netty规定好的某个HandlerAdapter
 * 2.这时我们自定义一个Handler,才能成为一个Handler
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/28 14:05
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据实际(这里我们可以读取客户端发送的信息)
     * 1. ChannelHandlerContext ctx:上下文对象,含有管道 pipeline,通道 channel,地址
     * 2. Object msg:客户端发送的数据 默认Object
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 比如这里我们有一个非常耗时间的业务 -> 异步执行 -> 提交到该channel对应的NioEventLoopGroup的taskQueue中
        // 解决方案1 用户程序自定义普通任务
      /*  ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发烧异常" + e.getMessage());
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端老哥~~2", CharsetUtil.UTF_8));
            }
        });
        // 解决方案2 用户程序自定义定时任务 该任务是提交到ScheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发烧异常" + e.getMessage());
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端老哥~~Schedule", CharsetUtil.UTF_8));
            }
        }, 5, TimeUnit.SECONDS);
        System.out.println("go on ...");*/

        System.out.println("服务器读取线程 " + Thread.currentThread().getName() + " channel = " + ctx.channel());
        System.out.println("server ctx = " + ctx);
        System.out.println("看看 channel 和 pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();// 本质是双向链表,出站入站
        // 将 msg 转成一个 ByteBuf(netty提供的)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址:" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 是 write + flush
        // 将数据写入到缓存,并刷新
        // 一般讲,我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端老哥~~", CharsetUtil.UTF_8));
    }

    // 处理异常,一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
