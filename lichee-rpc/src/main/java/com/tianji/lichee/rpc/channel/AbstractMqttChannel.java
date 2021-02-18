package com.tianji.lichee.rpc.channel;

public abstract class AbstractMqttChannel implements MqttChannel {
    private final MqttChannelPipeline pipeline;

    protected AbstractMqttChannel(){
        pipeline = new DefaultMqttChannelPipeline();
    }

    public MqttChannelPipeline pipeline() {
        return pipeline;
    }
}
