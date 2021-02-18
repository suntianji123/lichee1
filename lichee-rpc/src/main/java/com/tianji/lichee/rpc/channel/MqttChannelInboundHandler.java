package com.tianji.lichee.rpc.channel;


public interface MqttChannelInboundHandler extends MqttChannelHandler {

    void ChannelRegistered(MqttChannelHandlerContext ctx) throws Exception;
}
