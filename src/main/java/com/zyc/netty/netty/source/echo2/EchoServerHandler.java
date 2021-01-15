/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.zyc.netty.netty.source.echo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    // group 就是充当业务线程池,可以将任务提交到该线程池中-这里我们创建了16个线程
    static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        System.out.println("EchoServerHandler channelRead 的线程是=" + Thread.currentThread().getName());
       /*
        // 按照原来的方法处理耗时任务
        // 解决方案1 用户程序自定义普通任务(缺点就是还是同一个线程名。造成阻塞)
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    System.out.println("EchoServerHandler execute 的线程是=" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发烧异常" + e.getMessage());
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端老弟~~", CharsetUtil.UTF_8));
            }
        });
        */
        // 方法1.将任务提交到group线程池
       /* group.submit(() -> {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            String message = new String(bytes, StandardCharsets.UTF_8);
            // 休眠10秒
            Thread.sleep(10 * 1000);
            System.out.println("msg=" + message);
            System.out.println("EchoServerHandler submit.call 的线程是=" + Thread.currentThread().getName());
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端老弟~~", CharsetUtil.UTF_8));
            return null;
        });*/
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String message = new String(bytes, StandardCharsets.UTF_8);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端老弟~~", CharsetUtil.UTF_8));
        System.out.println("public 调用的线程是=" + Thread.currentThread().getName());
        System.out.println("go on msg=" + message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
