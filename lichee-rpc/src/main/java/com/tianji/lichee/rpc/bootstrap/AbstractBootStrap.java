package com.tianji.lichee.rpc.bootstrap;

import com.tianji.lichee.rpc.channel.MqttChannel;
import com.tianji.lichee.rpc.channel.MqttChannelHandler;

public abstract class AbstractBootStrap<B extends AbstractBootStrap<B,C>,C extends MqttChannel> {

    private volatile MqttChannelFactory<? extends C> mqttChannelFactory;

    private volatile MqttChannelHandler handler;

    protected AbstractBootStrap(){

    }

    public B handler(MqttChannelHandler handler){
        if(handler == null){
            throw new NullPointerException("handler");
        }
        this.handler = handler;
        return (B)this;
    }

    public B channel(Class<? extends C> channelClass){
        if(channelClass == null){
            throw new NullPointerException("channelClass");
        }
        return channelFactory(new BootstrapMqttChannelFactory<C>(channelClass));
    }

    public B channelFactory(MqttChannelFactory<? extends C> mqttChannelFactory){
        if(mqttChannelFactory == null){
            throw new NullPointerException("channelFactory");
        }

        if(this.mqttChannelFactory != null){
            throw new IllegalStateException("channelFactory set already");
        }

        this.mqttChannelFactory = mqttChannelFactory;
        return (B)this;
    }


    private static final class BootstrapMqttChannelFactory<C extends MqttChannel> implements MqttChannelFactory<C> {
        private final Class<? extends C> clazz;

        BootstrapMqttChannelFactory(Class<? extends C> clazz) {
            this.clazz = clazz;
        }

        public C newChannel() {
            try {
                return clazz.newInstance();
            } catch (Throwable t) {
                //todo:添加异常处理
            }
            return null;
        }

    }
}
