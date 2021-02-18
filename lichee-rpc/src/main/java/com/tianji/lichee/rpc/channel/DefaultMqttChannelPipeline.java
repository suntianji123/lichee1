package com.tianji.lichee.rpc.channel;

import com.tianji.lichee.common.ObjectUtil;
import com.tianji.lichee.common.StringUtil;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;

public class DefaultMqttChannelPipeline implements MqttChannelPipeline {

    private static final String HEAD_NAME = generateName0(HeadContext.class);
    private static final String TAIL_NAME = generateName0(TailContext.class);

    private final MqttChannel channel;
    private final AbstractChannelHandlerContext tail;
    private final AbstractChannelHandlerContext head;

    protected DefaultMqttChannelPipeline(MqttChannel channel){
        this.channel = ObjectUtil.checkNotEmpty(channel,"channel");
        tail = new TailContext(this);
        head = new HeadContext(this);
        head.next = tail;
        tail.prev = head;
    }

    private AbstractChannelHandlerContext newContext(ExecutorService executor,String name,MqttChannelHandler handler){
        return new DefaultMqttChannelHandlerContext(this,executor,name,handler);
    }

    public DefaultMqttChannelPipeline addLast(ExecutorService executor, MqttChannelHandler... handlers) {
        if(handlers == null){
            throw new NullPointerException("handlers");
        }

        for(MqttChannelHandler h : handlers){
            if(h == null){
                break;
            }

            addLast(executor,null,h);
        }

        return this;
    }

    public DefaultMqttChannelPipeline addLast(ExecutorService executor, String name, MqttChannelHandler handler) {
        final AbstractChannelHandlerContext newCtx;

        synchronized (this){
            newCtx = newContext(executor,filterName(name,handler),handler);
            addLast0(newCtx);
        }
        return this;
    }

    public MqttChannel channel() {
        return channel;
    }

    public final MqttChannelHandlerContext context(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }

        return context0(name);
    }

    public MqttChannelPipeline remove(MqttChannelHandler handler) {
        remove(getContextOrDie(handler));
        return this;
    }

    public MqttChannelPipeline fireChannelRegistered() {
        return this;
    }

    private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext ctx) {
        assert ctx != head && ctx != tail;

        synchronized (this) {
            remove0(ctx);
        }

        return ctx;
    }

    private static void remove0(AbstractChannelHandlerContext ctx) {
        AbstractChannelHandlerContext prev = ctx.prev;
        AbstractChannelHandlerContext next = ctx.next;
        prev.next = next;
        next.prev = prev;
    }

    private AbstractChannelHandlerContext getContextOrDie(MqttChannelHandler handler) {
        AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext) context(handler);
        if (ctx == null) {
            throw new NoSuchElementException(handler.getClass().getName());
        } else {
            return ctx;
        }
    }

    public final MqttChannelHandlerContext context(MqttChannelHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler");
        }

        AbstractChannelHandlerContext ctx = head.next;
        for (;;) {

            if (ctx == null) {
                return null;
            }

            if (ctx.handler() == handler) {
                return ctx;
            }

            ctx = ctx.next;
        }
    }

    private void addLast0(AbstractChannelHandlerContext newCtx){
        AbstractChannelHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
    }

    private String filterName(String name,MqttChannelHandler channelHandler){
        if(name == null){
            return generateName0(channelHandler.getClass());
        }

        checkDuplicateName(name);
        return name;
    }

    private void checkDuplicateName(String name){
        if(context0(name) != null){
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }

    private AbstractChannelHandlerContext context0(String name){
        AbstractChannelHandlerContext context = head.next;
        while (context != null){
            if(context.name.equals(name)){
                return context;
            }

            context = context.next;
        }

        return null;
    }

    private static String generateName0(Class<?> handlerType){
        return StringUtil.simpleClassName(handlerType) + "#0";
    }



    final class HeadContext extends AbstractChannelHandlerContext implements MqttChannellOutboundHandler{

        HeadContext(DefaultMqttChannelPipeline pipeline){
            super(pipeline,null,HEAD_NAME,false,true);
        }

        public MqttChannelHandler handler() {
            return this;
        }
    }

    final class TailContext extends AbstractChannelHandlerContext implements MqttChannelInboundHandler{
        TailContext(DefaultMqttChannelPipeline pipeline){
            super(pipeline,null,TAIL_NAME,true,false);
        }
        public void ChannelRegistered(MqttChannelHandlerContext ctx) throws Exception {

        }

        public MqttChannelHandler handler() {
            return this;
        }
    }
}
