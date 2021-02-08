package com.tianji.lichee.gate.protocol;

/**
 * 包类型
 */
public enum  PacketType {
    HANDSHAKE((byte)01),
    HANDSHAKE_ACK((byte)0x02),
    HEAERT_BEAT((byte)0x03),
    DATA((byte)0x4);

    byte code;

    PacketType(byte code){
        this.code = code;
    }

    public static PacketType valueOf(byte code){
        for(PacketType type : PacketType.values()){
            if(type.code == code){
                return type;
            }
        }

        return null;
    }
}
