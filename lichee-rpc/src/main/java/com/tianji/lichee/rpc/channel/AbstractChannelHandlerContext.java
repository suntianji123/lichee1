package com.tianji.lichee.rpc.channel;

import com.tianji.lichee.common.ObjectUtil;

import java.util.concurrent.ExecutorService;

abstract class AbstractChannelHandlerContext implements MqttChannelHandlerContext{
    volatile AbstractChannelHandlerContext next;
    volatile AbstractChannelHandlerContext prev;

    final ExecutorService executor;

    final String name;

    final DefaultMqttChannelPipeline pipeline;

    final boolean inbound;

    final boolean outbound;

    AbstractChannelHandlerContext(DefaultMqttChannelPipeline pipeline,ExecutorService executor,
                                  String name,boolean inbound,boolean outbound){
        this.name = ObjectUtil.checkNotEmpty(name,"name");
        this.pipeline = pipeline;
        this.executor = executor;
        this.inbound = inbound;
        this.outbound = outbound;
    }

    public MqttChannel channel() {
        return pipeline.channel();
    }

    public MqttChannelPipeline pipeline() {
        return pipeline;
    }

    public MqttChannelHandlerContext fireChannelRegistered() {
        return this;
    }
}
