package com.tianji.lichee.gate.common;

import io.netty.channel.Channel;

import java.net.SocketAddress;

public class GateHelper {

    /**
     * 获取channel远程地址
     * @param channel channel连接
     * @return
     */
    public static String parseChannelRemoteAddr(final Channel channel){
        if(null == channel){
            return "";
        }

        SocketAddress remote = channel.remoteAddress();
        final String addr = remote != null ? remote.toString():"";

        if(addr.length() > 0){
            int index = addr.lastIndexOf("/");
            if(index >= 0){
                return addr.substring(index + 1);
            }

            return addr;
        }

        return "";
    }
}
