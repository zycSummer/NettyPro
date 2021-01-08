package com.zyc.netty.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: MyTextWebSocketFrameHandler.java
 * @Package: com.jet.cloud.deepmind
 * @Description: TextWebSocketFrame 类型，表示一个文本帧(frame)，
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/8 10:12
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到消息 " + msg.text());
        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间 " + LocalDateTime.now() + " " + msg.text()));
    }

    /**
     * 当web客户端连接后,触发方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // id 表示唯一的值,LongText 是唯一的 ShortText 不是唯一的
        System.out.println("handlerAdded 被调用 " + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用 " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用 " + ctx.channel().id().asLongText());
        System.out.println("handlerRemoved 被调用 " + ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close();
    }
}
