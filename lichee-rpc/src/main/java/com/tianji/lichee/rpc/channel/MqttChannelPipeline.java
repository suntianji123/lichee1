package com.tianji.lichee.rpc.channel;

import java.util.concurrent.ExecutorService;

public interface MqttChannelPipeline {

    DefaultMqttChannelPipeline addLast(ExecutorService executor,MqttChannelHandler... handlers);

    DefaultMqttChannelPipeline addLast(ExecutorService executor,String name,MqttChannelHandler handler);

    MqttChannel channel();

    MqttChannelHandlerContext context(MqttChannelHandler handler);

    MqttChannelHandlerContext context(String name);

    MqttChannelPipeline remove(MqttChannelHandler handler);

    MqttChannelPipeline fireChannelRegistered();
}
