package com.tianji.lichee.rpc;

import com.tianji.lichee.rpc.bootstrap.ServerBootStrap;
import com.tianji.lichee.rpc.channel.MqttChannelInitializer;
import com.tianji.lichee.rpc.channel.MqttChannelPipeline;
import com.tianji.lichee.rpc.channel.ServerMqttChannel;

public class RpcServer implements RpcService {
    private final ServerBootStrap serverBootStrap = new ServerBootStrap();

    public void start() {
        serverBootStrap.channel(ServerMqttChannel.class).
                handler(new MqttChannelInitializer<ServerMqttChannel>() {
                    protected void initChannel(ServerMqttChannel ch) throws Exception {
                        MqttChannelPipeline pipeline = ch.pipeline();
                        //添加handler
                    }
                });
    }

    public void shutdown() {

    }
}
