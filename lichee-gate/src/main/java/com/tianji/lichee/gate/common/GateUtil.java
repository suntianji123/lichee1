package com.tianji.lichee.gate.common;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class GateUtil {

    //todo:添加日志


    public static void closeChannel(Channel channel){
        final String addrRemoting = GateHelper.parseChannelRemoteAddr(channel);
        channel.close().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //todo:添加记录日志
            }
        });
    }
}
