package com.start.nio.socket;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 服务端
 * 使用NIO进行socket连接
 * 之所以这个类实现Runnable接口，是为了让Selector变成一个线程跑。一直处于轮询状态
 */
public class Server implements Runnable {
    //1.Selector，管理所有通道
    private Selector selector;
    //2.Buffer
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        new Thread(new Server(9999)).start();
    }

    //构造方法中设置，注意此时，server服务未开始监听客户端
    public Server(int port) {
        try {
            //1.打开Selector
            this.selector = Selector.open();
            //2.打开ServerSocketChannel  也算是一种通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //3.设置ServerSocketChannel为非阻塞
            ssc.configureBlocking(false);
            //4.地址
            ssc.bind(new InetSocketAddress(port));
            //5.将ServerSocketChannel注册到Selector,并监听阻塞事件
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务开启，端口为" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                //1.Selector开始监听(选择)，是阻塞的方法
                this.selector.select();
                //2.返回select()方法选择到的结果集. selectedKeys()返回的是结果集
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                //3.进行遍历
                while (keys.hasNext()) {
                    //4.获取被选中的Channel的key
                    SelectionKey key = keys.next();
                    //5.直接从迭代器中移除这个key
                    keys.remove();
                    //6.如果key是有效的
                    if (key.isValid()) {
                        //7.如果为阻塞状态，
                        // 因为注册SeverSocketChannel时，监听的状态为阻塞，4
                        // 所以，为阻塞状态的通道一定是ServerSocketChannel
                        if (key.isAcceptable()) {
                            this.accept(key);
                        }
                        //8.如果为可读状态
                        if (key.isReadable()) {
                            this.read(key);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取事件
     * 也就是当SocketChannel已经被监听了，然后监听到读状态，进行的方法
     */
    private void read(SelectionKey key) {
        try {
            //1.清空缓冲区旧数据
            this.readBuffer.clear();
            //2.获取读状态的channel
            SocketChannel sc = (SocketChannel) key.channel();
            //3.读取数据
            int count = sc.read(this.readBuffer);
            //4.如果没有数据
            if(count == -1){
                sc.close();//关闭通道
                key.cancel();//取消注册，相当于这个通道关闭连接,不再监听
                return;
            }
            ///5.有数据就读取，读取之前要进行复位方法(把position和limit复位)
            this.readBuffer.flip();
            //6.根据Buffer可读长度，创建相应大小的数组
            byte[] bytes = new byte[readBuffer.remaining()];
            //7.接收数据,将buffer的数据复制给bytes。
            this.readBuffer.get(bytes);
            //8.打印结果
            String body = new String(bytes).trim();
            System.out.println("server:" + body);

            //9.可以写会数据给客户端
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行这个方法的channel通道一定ServerSocketChannel
     * 每次有客户端连接过来，就会获取到这个server，然后取出
     * 因为每次客户端连接过来，ServerSocketChannel都会被阻塞
     * socketChannel，注册到Selector，监听读取状态
     */
    private void accept(SelectionKey key) {
        try {
            //1.获取ServerSocketChannel
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //2.执行阻塞方法
            SocketChannel sc = ssc.accept();
            //3.设置阻塞模式
            sc.configureBlocking(false);
            //4.注册到Selector上，并监听读取状态
            sc.register(this.selector,SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
