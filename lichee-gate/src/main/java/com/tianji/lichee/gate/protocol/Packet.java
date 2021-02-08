package com.tianji.lichee.gate.protocol;

/**
 * 包类
 */
public class Packet {

    private PacketType type;

    private int length;

    private byte[] data;

    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
