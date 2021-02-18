package com.tianji.lichee.rpc.channel;

public interface MqttChannelHandlerContext {
    MqttChannel channel();

    MqttChannelPipeline pipeline();

    MqttChannelHandler handler();

    MqttChannelHandlerContext fireChannelRegistered();
}
