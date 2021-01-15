package com.zyc.netty.netty.dubborpc.provider;

import com.zyc.netty.netty.dubborpc.publicinterface.HelloService;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 当有消费方调用该方法时, 就返回一个结果
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/15 13:51
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class HelloServiceImpl implements HelloService {
    private static int count = 0;

    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        // 根据msg返回不同的结果
        if (msg != null) {
            return "你好客户端,我已经收到你的消息 [" + msg + "] 第" + (++count) + "次";
        } else {
            return "你好客户端,我已经收到你的消息 ";
        }
    }
}
