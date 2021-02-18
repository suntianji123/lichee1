package com.tianji.lichee.rpc.channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class MqttChannelInitializer<C extends MqttChannel> implements MqttChannelInboundHandler {

    private final ConcurrentMap<MqttChannelHandlerContext,Boolean> initMap = new ConcurrentHashMap<MqttChannelHandlerContext, Boolean>();

    public void ChannelRegistered(MqttChannelHandlerContext ctx) throws Exception {
        if(initChannel(ctx)){
            ctx.pipeline().fireChannelRegistered();
        }else{
            ctx.fireChannelRegistered();
        }
    }

    protected abstract void initChannel(C ch) throws Exception;

    private boolean initChannel(MqttChannelHandlerContext ctx) throws Exception{
        if(initMap.putIfAbsent(ctx,Boolean.TRUE) == null){
            try{
                initChannel((C)ctx.channel());
            }catch (Throwable casue){
                //todo:异常处理
            }finally {
                remove(ctx);
            }
            return true;
        }
        return false;
    }

    private void remove(MqttChannelHandlerContext ctx){
        try{
            MqttChannelPipeline pipeline = ctx.pipeline();
            if(pipeline.context(this) != null){
                pipeline.remove(this);
            }
        }finally {
            initMap.remove(ctx);
        }
    }
}
