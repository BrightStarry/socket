package com.start.aio.socket;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * 当有客户端连接到服务端， 使用这个类处理
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

	/**
	 * 连接成功的方法
	 */
	@Override
	public void completed(AsynchronousSocketChannel asc, Server attachment) {
		//当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
		//也就是继续接受其他客户端的请求，也就相当于BIO中的while(true){server.accept();}
		attachment.assc.accept(attachment, this);
		read(asc);
	}

	/**
	 * 连接失败的方法
	 */
	@Override
	public void failed(Throwable exc, Server attachment) {
		exc.printStackTrace();
	}

	private void read(final AsynchronousSocketChannel asc) {
		//读取数据
		ByteBuffer buf = ByteBuffer.allocate(1024);
		//异步读，第三个参数，这个内部类的作用是处理异步返回的结果（相当于就是回调函数）
		asc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
			/**
			 * 如果读取成功
			 */
			@Override
			public void completed(Integer resultSize, ByteBuffer attachment) {
				//进行读取之后,重置标识位
				attachment.flip();
				//获得读取的字节数
				System.out.println("Server -> " + "收到客户端的数据长度为:" + resultSize);
				//获取读取的数据
				String resultData = new String(attachment.array()).trim();
				System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
				String response = "服务器响应, 收到了客户端发来的数据: " + resultData;
				write(asc, response);
			}

			/**
			 * 如果读取失败
			 */
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				exc.printStackTrace();
			}
		});
	}

	/**
	 * 向客户端写入数据
	 */
	private void write(AsynchronousSocketChannel asc, String response) {
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.put(response.getBytes());
			buf.flip();
			asc.write(buf).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	


}
