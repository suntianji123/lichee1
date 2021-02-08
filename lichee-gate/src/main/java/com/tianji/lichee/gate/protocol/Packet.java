package com.tianji.lichee.gate.protocol;

import java.nio.ByteBuffer;

/**
 * 包类
 */
public class Packet {

    /**
     * 包类型
     */
    private PacketType type;


    /**
     * 包体
     */
    private transient byte[] data;

    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * 编码消息头
     * @return
     */
    public ByteBuffer encodeHeader(){
        return encodeHeader(this.data != null?this.data.length:0);
    }

    /**
     * 编码消息头
     * @param bodyLength 消息体长度
     * @return
     */
    public ByteBuffer encodeHeader(int bodyLength){
        int length = 5 + bodyLength;
        ByteBuffer result = ByteBuffer.allocate(5);
        result.putInt(length);
        result.put(type.code);
        result.flip();
        return result;
    }

    /**
     * 解码
     * @param byteBuffer
     * @return
     */
    public static Packet decode(ByteBuffer byteBuffer){
        //消息头长度
        int length = byteBuffer.limit();
        Packet packet = new Packet();
        packet.type = PacketType.valueOf(byteBuffer.get());

        int bodyLength = length - 1;
        byte[] bodyData = null;
        if(bodyLength > 0){
            bodyData = new byte[bodyLength];
            byteBuffer.get(bodyData);
        }
        packet.data = bodyData;
        return packet;
    }


































}
