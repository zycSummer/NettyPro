package com.zyc.netty.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/7 14:54
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
