package com.zyc.netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/7 13:38
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    // 使用HashMap 管理 私聊扩展
   //  public static Map<String, Channel> channelMap = new HashMap<>();

    // 定义一个channel组,管理所有的channel
    // GlobalEventExecutor.INSTANCE是个全局的事件执行器,是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // handlerAdded 表示连接建立,一旦连接,第一个被执行
    // 将当前的 channel 加入到 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其它在线的客户端
        /**
         * 该方法会将 channelGroup 中所有的channel 遍历,并发送消息
         * 我们不需要自己遍历
         */
        channelGroup.writeAndFlush("【客户端】" + " " + sdf.format(new Date()) + " " + channel.remoteAddress() + " 加入聊天\n");
        channelGroup.add(channel);
//        channelMap.put("id100", channel);
    }

    // 断开连接,将xx客户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【客户端】" + " " + sdf.format(new Date()) + " " + channel.remoteAddress() + " 离开了\n");
        System.out.println("当前channelGroup size:" + channelGroup.size());
    }

    // 表示channel 处于活动的状态,提示 xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户端】" + " " + ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + " " + " 上线了~");
    }

    // 表示channel 处于不活动的状态,提示 xx上线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户端】" + " " + ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + " " + " 离线了~");
    }

    // 读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("【客户端】" + " " + ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + " " + " 发消息是:" + msg);
        // 获取到当前的channel
        Channel channel = ctx.channel();
        // 这时我们遍历channelGroup,根据不能的情况回送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) { // 不是当前的channel,转发消息
                ch.writeAndFlush("【客户】" + " " + sdf.format(new Date()) + " " + channel.remoteAddress() + " 发送了消息" + msg + "\n");
            } else {// 回显自己发送的消息给自己
                ch.writeAndFlush("【自己】" + " " + sdf.format(new Date()) + " " + channel.remoteAddress() + " 发送了消息" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
    }
}
