package stream.sequence;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 *序列（合并） 字节 输入 流 
 *当需要从多个输入流中读取数据的时候可以用这个
 *它可以将与之连接的多个输入流从第一个开始读取知道最后
 *一个输入流的文件末尾为止
 *可接收枚举类所封装的多个字节流对象
 *
 *突然发现还是称为序列流比较适合， 因为是把多个输入流合并成一个序列 读取嘛
 */
public class SequenceExample {
	public static void main(String[] args){
		//声明一个合并流对象
		SequenceInputStream sis = null;
		//声明缓存输出流
		BufferedOutputStream bos = null;
		//声明缓存输入流,用来包裹SequencsInputStream
		BufferedInputStream bis = null;
		
		try {
			/**
			 * Vector和ArrayList很像，不同Vector线程同步（安全）的
			 * 这里使用Vector应该是考虑到了多个线程同时读取的问题
			 * 
			 * 给SequenceInputStream 创建对象的时候，只能一个个传入输入流，或者用枚举传入
			 * Enumeration是一个接口.定义了从数据结构获取连续数据的方法
			 */
			//构建要读取的输入流序列
			Vector<InputStream> vector = new Vector<InputStream>();
			vector.add(new FileInputStream("D:/桌面/Desktop/IOTest/1.txt"));
			vector.add(new FileInputStream("D:/桌面/Desktop/IOTest/2.txt"));
			vector.add(new FileInputStream("D:/桌面/Desktop/IOTest/3.txt"));
			//把vector转成Enumeration接口类型
			Enumeration<InputStream> e = vector.elements();
			
			//创建序列输入流对象
			sis = new SequenceInputStream(e);
			//创建缓存输出流对象
			bos = new BufferedOutputStream(new FileOutputStream("D:/桌面/Desktop/IOTest/4.txt"));
			//创建缓存输入流对象
			bis = new BufferedInputStream(sis);
			
			int length = 0;//每次读取的字节数
			byte[] buffer = new byte[512];//缓冲区
			int bufferLength = buffer.length;//缓冲字节数组长度
			
			while((length = bis.read(buffer,0,bufferLength)) != -1){
				bos.write(buffer,0,length);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				sis.close();
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
