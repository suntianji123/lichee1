package com.tianji.lichee.gate.netty;

public class NettySystemConfig {

    public static final String COM_LICHEE_GATE_SOCKET_SNDBUF_SIZE =
            "com.lichee.gate.socket.sndbuf.size";
    public static final String COM_LICHEE_GATE_SOCKET_RCVBUF_SIZE =
            "com.lichee.gate.socket.rcvbuf.size";

    public static int socketSndbufSize =
            Integer.parseInt(System.getProperty(COM_LICHEE_GATE_SOCKET_SNDBUF_SIZE, "65535"));
    public static int socketRcvbufSize =
            Integer.parseInt(System.getProperty(COM_LICHEE_GATE_SOCKET_RCVBUF_SIZE, "65535"));
}
