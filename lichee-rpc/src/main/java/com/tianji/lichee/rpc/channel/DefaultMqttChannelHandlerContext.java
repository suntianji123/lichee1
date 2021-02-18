package com.tianji.lichee.rpc.channel;

import java.util.concurrent.ExecutorService;

public class DefaultMqttChannelHandlerContext extends AbstractChannelHandlerContext {
    private final MqttChannelHandler handler;

    DefaultMqttChannelHandlerContext(DefaultMqttChannelPipeline pipeline, ExecutorService executor, String name, MqttChannelHandler handler) {
        super(pipeline, executor, name, isInbound(handler),isOutbound(handler));
        if(handler == null){
            throw new NullPointerException("handler");
        }
        this.handler = handler;
    }

    private static boolean isInbound(MqttChannelHandler handler){
        return handler instanceof MqttChannelInboundHandler;
    }

    private static boolean isOutbound(MqttChannelHandler handler){
        return handler instanceof MqttChannellOutboundHandler;
    }
}
