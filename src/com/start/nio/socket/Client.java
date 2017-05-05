package com.start.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO Socket
 * 客户端
 *
 * 目前只能服务端接收客户端写入的数据，如果需要客户端接收服务端写入的数据，
 * 就同样需要在Client这个类中建立一个Selector，监听ServerSocketChannel
 */
public class Client {

    public static void main(String[] args) throws Exception {
        //创建连接地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //打开通道
        SocketChannel sc = SocketChannel.open();
        //进行连接
        sc.connect(address);

        while(true){
            //创建一个数组，然后使用使用Console的输入
            byte[] bytes = new byte[1024];
            System.in.read(bytes);

            //将数据放入缓冲区
            buffer.put(bytes);
            //复位
            buffer.flip();
            //将Buffer写入
            sc.write(buffer);
            //清空缓冲区
            buffer.clear();
        }


    }
}
