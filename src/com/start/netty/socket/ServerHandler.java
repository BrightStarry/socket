package com.start.netty.socket;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * 服务端处理类
 */
public class ServerHandler extends ChannelHandlerAdapter {
    /**
     * 重写读取方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * Netty中不再需要flip()操作
         */

        //缓冲区
        ByteBuf byteBuf = (ByteBuf)msg;
        //创建字节数组 ，长度等于 缓冲区中可以读字节数组的长度
        byte[] data = new byte[byteBuf.readableBytes()];
        //将缓冲区数据写入字节数组
        byteBuf.readBytes(data);
        String request = new String(data,"UTF-8");
        System.out.println("server:" + request);
        //写回数据给客户端
        String result = "反馈信息";
        ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer(result.getBytes()));
        /**
         * 添加监听器
         */
        //可以自己实现
//        future.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                //断言，判断 两个future是否相等
//                assert future == channelFuture;
//                ctx.close();
//            }
//        });
        //也可以直接使用已有的
        future.addListener(ChannelFutureListener.CLOSE);



//        //释放连接
//        ReferenceCountUtil.release(msg);

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
