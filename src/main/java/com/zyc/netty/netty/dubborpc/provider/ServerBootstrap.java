package com.zyc.netty.netty.dubborpc.provider;


import com.zyc.netty.netty.dubborpc.netty.NettyServer;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: ServerBootstrap 回启动一个服务的提供者，就是NettyServer
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/15 13:55
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
