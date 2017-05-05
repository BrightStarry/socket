package com.start.netty.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * 客户端处理类
 */
public class ClientHandler extends ChannelHandlerAdapter {
    /**
     * 通道激活方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 重写读方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        String request = new String(data,"UTF-8");
        System.out.println("client:" + request);
    }

    /**
     * 重写捕获异常方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
