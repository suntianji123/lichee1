package com.tianji.lichee.gate.netty;

import com.tianji.lichee.gate.common.GateUtil;
import com.tianji.lichee.gate.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

@ChannelHandler.Sharable
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    //todo:添加日志

    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        try{
            ByteBuffer header = packet.encodeHeader();
            out.writeBytes(header);
            byte[] body = packet.getData();
            if(body != null){
                out.writeBytes(body);
            }
        }catch (Exception e){
            if(packet != null){
                //todo:记录日志
            }
            GateUtil.closeChannel(ctx.channel());
        }
    }
}
