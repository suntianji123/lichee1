package com.tianji.lichee.rpc.bootstrap;

import com.tianji.lichee.rpc.channel.MqttChannel;

public interface MqttChannelFactory<T extends MqttChannel> {
    T newChannel();
}
