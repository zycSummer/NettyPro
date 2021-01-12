package com.zyc.netty.netty.protocoltcp;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description: 协议包
 * @author: 济中节能 zhuyicheng
 * @date: 2021/1/12 13:24
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class MessageProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
