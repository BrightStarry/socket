package com.start.netty.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
    服务端
 */
public class Server {
    public static void main(String[] args) throws Exception {
        //1 用于接收Client的连接 的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2 用于实际业务操作的线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //3 创建一个辅助类Bootstrap（引导程序）,对server进行配置
        ServerBootstrap serverBootStrap = new ServerBootstrap();
        //4 将两个线程组加入 bootstrap
        serverBootStrap.group(bossGroup,workerGroup)
                /**
                 * 下面的 childXXX是配置子通道
                 * 例如childOption
                 * 那么option就是配置本通道
                 */
                //指定使用这种类型的通道
                .channel(NioServerSocketChannel.class)
                //使用 childHandler 绑定具体的事件处理器
                /**
                 * 这里应该是通道的初始化事件，
                 * 指定了子通道的handler类
                 * 之所以是子通道，应该是因为这个serverHandler处理的实际上还是客户端通道的读事件，
                 * 也就是当客户端写入数据的时候，workGroup管理下的客户端通道的读事件就被触发。
                 */
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        /**
                         * 对粘包拆包的处理
                         *  分隔符，然后只要在handler中的写入方法中，每个消息末尾加个 "$_"就可以了
                         *  定长
                         */
//                        //设置特殊分割符
//                        ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
//                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
                        //定长处理
                        socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(5));
                        //设置字符串形式的解码  这样serverHandler中获取到的msg可以直接(String)msg转为string
                        socketChannel.pipeline().addLast(new StringDecoder());

                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                })
                /**
                 * TCP连接的 参数
                 * tcp三次握手：
                 *  客户端发送有SYN标志的包（第一次）
                 *  服务器收到后，向客户端发送SYN ACK（第二次）
                 *  此时，TCP内核模块把客户端连接放入A队列，
                 *  然后服务器收到客户端再次发来的ACK时（第三次）
                 *  TCP内核把客户端连接放入B队列，连接完成,完成accept()方法
                 *  此时，TCP内核会被客户端连接从队列B中取出。完成。
                 *
                 *  A队列和B队列长度之和就是backlog，如果大于backlog，新连接会被拒绝
                 *  注意，backlog对程序支持的连接数并无影响，backlog只影响还未完成accept()方法的连接。
                 */
                .option(ChannelOption.SO_BACKLOG,128)
                //保持连接
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                //发送缓冲区大小
                .option(ChannelOption.SO_SNDBUF,32 * 1024)
                //接收缓冲区大小
                .option(ChannelOption.SO_RCVBUF,32 * 1024);
        //5 绑定端口，进行监听 异步的  可以开启多个端口监听
        ChannelFuture future = serverBootStrap.bind(8888).sync();
//        ChannelFuture future2 = serverBootStrap.bind(8888).sync();
        //6 关闭前阻塞
        future.channel().closeFuture().sync();
//        future2.channel().closeFuture().sync();

        //7 关闭线程组（优雅的。。。）
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }
}
