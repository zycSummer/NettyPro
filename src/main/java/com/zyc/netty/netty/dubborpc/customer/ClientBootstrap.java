package com.zyc.netty.netty.dubborpc.customer;

import com.zyc.netty.netty.dubborpc.netty.NettyClient;
import com.zyc.netty.netty.dubborpc.publicinterface.HelloService;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/15 15:17
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class ClientBootstrap {
    // 定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        // 创建一个消费者
        NettyClient customer = new NettyClient();
        // 创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);
        // 通过代理对象调用服务提供者的方法(服务)
        for (;;) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String res = service.hello("你好 dubbo~");
            System.out.println("调用的结果 res=" + res);
        }
    }
}
