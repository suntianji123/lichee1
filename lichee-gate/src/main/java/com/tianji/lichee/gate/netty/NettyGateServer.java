package com.tianji.lichee.gate.netty;

import com.tianji.lichee.gate.GateServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网关服务器类
 */
public class NettyGateServer implements GateServer {
    private final ServerBootstrap serverBootstrap;

    private final EventLoopGroup eventLoopGroupSelector;

    private final EventLoopGroup eventLoopGroupBoss;

    private final NettyServerConfig nettyServerConfig;

    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    public NettyGateServer(final NettyServerConfig nettyServerConfig){
        this.serverBootstrap = new ServerBootstrap();
        this.nettyServerConfig = nettyServerConfig;
        this.eventLoopGroupBoss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r,String.format("Gate_NettyNIOBOSS_%d",this.threadIndex.incrementAndGet()));
            }
        });
        this.eventLoopGroupSelector = new NioEventLoopGroup(nettyServerConfig.getServerSelectorThreads(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);
            private int threadTotal = nettyServerConfig.getServerSelectorThreads();

            public Thread newThread(Runnable r) {
                return new Thread(r,String.format("Gate_NettyServerNIOSelector_%d_%d",threadTotal,threadIndex.getAndIncrement()));
            }
        });
    }

    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(nettyServerConfig.getServerWorkerThreads(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);
            private int threadTotal = nettyServerConfig.getServerWorkerThreads();

            public Thread newThread(Runnable r) {
                return new Thread(r,String.format("Gate_NettyServerCodecThread_%d_%d",threadTotal,threadIndex.getAndIncrement()));
            }
        });

        ServerBootstrap childHandler = this.serverBootstrap.group(this.eventLoopGroupBoss,this.eventLoopGroupSelector)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .option(ChannelOption.SO_REUSEADDR,true)
                .option(ChannelOption.SO_KEEPALIVE,false)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.SO_SNDBUF,nettyServerConfig.getServerSocketSndBufSize())
                .childOption(ChannelOption.SO_RCVBUF,nettyServerConfig.getServerSocketRcvBufSize())
                .localAddress(new InetSocketAddress(this.nettyServerConfig.getListenPort()))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //todo:添加handler
                    }
                });
        if(nettyServerConfig.isServerPooledByteBufAllocatorEnable()){
            childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        }
    }

    public void shutdown() {
        try{
            this.eventLoopGroupBoss.shutdownGracefully();
            this.eventLoopGroupSelector.shutdownGracefully();

            if(this.defaultEventExecutorGroup != null){
                this.defaultEventExecutorGroup.shutdownGracefully();
            }
        }catch (Exception e){
            //todo:记录日志
        }
    }
}
