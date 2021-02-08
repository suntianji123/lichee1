package com.tianji.lichee.gate.netty;

import com.tianji.lichee.gate.common.GateUtil;
import com.tianji.lichee.gate.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * 包解码器
 */
public class PacketDecoder extends LengthFieldBasedFrameDecoder {

    private static final int FRAME_MAX_LENGTH =
            Integer.parseInt(System.getProperty("com.lichee.gate.frameMaxLength", "16777216"));

    public PacketDecoder(){
        super(FRAME_MAX_LENGTH,0,4,0,4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = null;
        try{
            frame = (ByteBuf) super.decode(ctx,in);
            if(null == frame){
                return null;
            }

            ByteBuffer byteBuffer = frame.nioBuffer();
            return Packet.decode(byteBuffer);
        }catch (Exception e){
            //todo:记录日志
            GateUtil.closeChannel(ctx.channel());
        }finally {
            if(null != frame){
                frame.release();
            }
        }
        return null;
    }
}
