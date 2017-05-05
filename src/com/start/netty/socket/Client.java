package com.start.netty.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //创建线程组
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();
        //创建引导程序
        Bootstrap bootstrap = new Bootstrap();
        //将线程组放入引导程序
        bootstrap.group(workLoopGroup)
                //使用指定的通道类
                .channel(NioSocketChannel.class)
                //设置日志
                .handler(new LoggingHandler(LogLevel.INFO))
                //重写通道初始化方法
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        /**
                         * 使用Marshalling框架对传输的数据进行序列化 服务端客户端都需要加下面两句代码
                         * 我复制了下代码，有错，所以没去实现，总的来说就是随便用一种序列化框架
                         * 然后写一个序列化框架对应的类，实现将对象序列化和反序列化的方法就可以了。
                         * 无需其他操作，write()方法可以直接把对象x输出
                         * handler的read()方法也可以直接把Object msg强转成对应的对象x。
                         */
//                        socketChannel.pipeline().addLast(xxx.buildMarshallingDecoder())
//                        socketChannel.pipeline().addLast(xxx.buildMarshallingEncoder())

                        //超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
                        socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                        //每次初始化出通道的时候就创建一个ClientHandel处理该通道
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

        //连接到服务端 返回的非阻塞异步通道
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8888).sync();

        /**
         * Unpooled.copieBuffer应该是把字节数组转换成 ByteBuf对象
         */
        //向服务端写入数据
        future.channel().writeAndFlush(Unpooled.copiedBuffer("testSend".getBytes()));

        //关闭
        future.channel().closeFuture().sync();
        workLoopGroup.close();
    }
}
