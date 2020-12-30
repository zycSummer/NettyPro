package com.zyc.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: TestHttpServerHandler.java
 * @Package: com.jet.cloud.deepmind
 * @Description: 1.SimpleChannelInboundHandler 是  ChannelInboundHandlerAdapter 子类
 * 2.HttpObject 客户端和服务器端 相互通讯的数据被封装成 HttpObject 类型
 * @author: 济中节能 zhuyicheng
 * @date: 2020/12/30 10:30
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * channelRead0 读取客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断 msg 是不是HttpRequest 请求
        if (msg instanceof HttpRequest) {
            System.out.println("ctx 类型="+ctx.getClass());
            System.out.println("pipeline hashcode=" + ctx.pipeline().hashCode() + " TestHttpServerHandler hash=" + this.hashCode());

            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());

            // 获取到
            HttpRequest httpRequest = (HttpRequest) msg;
            // 获取uri
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico, 不做响应");
                return;
            }

            // 回复信息给浏览器 (http 协议)
            ByteBuf content = Unpooled.copiedBuffer("hello http,我是服务器", CharsetUtil.UTF_8);
            // 构造一个http的响应,即httpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
