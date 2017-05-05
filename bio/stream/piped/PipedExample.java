package stream.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 *通道 字节 IO 实例 
 */
public class PipedExample {
	//测试方法
	public static void main(String[] args) throws Exception{
		Send send = new Send();
		Receive receive = new Receive();
		//把通道输出流 和 通道输入流连接
		send.getOut().connect(receive.getInput());
		
		//开启线程
		new Thread(send).start();
		new Thread(receive).start();
	}
}

/**
 *消息发送类 
 */
class Send implements Runnable{
	//管道输出流
	private PipedOutputStream out = null;
	public Send() {
		out = new PipedOutputStream();
	}
	
	/**获取管道输出流*/
	public PipedOutputStream getOut(){
		return this.out;
	}
	
	@Override
	public void run() {
		String message = "hello,world";
		try {
			out.write(message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

/**
 *接收消息类
 */
class Receive implements Runnable{
	private PipedInputStream input = null;
	
	public Receive() {
		input = new PipedInputStream();
	}
	
	/**获取输入流方法*/
	public PipedInputStream getInput(){
		return this.input;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1000];
		
		try {
			this.input.read(buffer);
			
			System.out.println("接受的内容：" + new String(buffer,"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
