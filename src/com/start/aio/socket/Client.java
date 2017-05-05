package com.start.aio.socket;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * AIO
 * 客户端
 */
public class Client implements Runnable{

	//客户端 socketChannel
	private AsynchronousSocketChannel asc ;

	//创建客户端时打开通道。
	public Client() throws Exception {
		asc = AsynchronousSocketChannel.open();
	}

	//将通道与服务端建立连接
	public void connect(){
		asc.connect(new InetSocketAddress("127.0.0.1", 8765));
	}

	//往服务端写入数据
	public void write(String request){
		try {
			//将要写入的string转成自己数组，再转成buffer，然后写入通道
			asc.write(ByteBuffer.wrap(request.getBytes())).get();
			//写入完成后，读取服务端返回的数据
			read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//从服务端读取数据
	private void read() {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		try {
			//因为是异步，所以read()方法返回的是Future对象，然后使用future.get()获取异步操作的结果
			asc.read(buf).get();
			buf.flip();
			byte[] respByte = new byte[buf.remaining()];
			buf.get(respByte);
			System.out.println(new String(respByte,"utf-8").trim());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	//一直保持服务端不停止
	@Override
	public void run() {
		while(true){
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		Client c1 = new Client();
		c1.connect();
		
		Client c2 = new Client();
		c2.connect();
		
		Client c3 = new Client();
		c3.connect();
		
		new Thread(c1, "c1").start();
		new Thread(c2, "c2").start();
		new Thread(c3, "c3").start();
		
		Thread.sleep(1000);
		
		c1.write("c1 aaa");
		c2.write("c2 bbbb");
		c3.write("c3 ccccc");
	}
	
}
