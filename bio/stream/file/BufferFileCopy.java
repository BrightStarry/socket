package stream.file;

import java.io.*;

/**
 *有缓冲区的文件拷贝 
 */
public class BufferFileCopy {
	public static void main(String[] args){
		byte[] buffer = new byte[512];//缓冲区
		int bufferLength = buffer.length;
		int length = 0;//当最后一次读取不足512字节时，用来确认写入的长度
		BufferedInputStream fis = null;
		BufferedOutputStream fos = null;
		
		try {
			fis = new BufferedInputStream(
					new FileInputStream("D:/桌面/Desktop/IOTest/1.jpg"));
			fos = new BufferedOutputStream(
					new FileOutputStream("D:/桌面/Desktop/IOTest/2.jpg"));
			
			/**
			 * 这里需要注意的是最好使用这个三个参数的read()方法，因为效率高
			 */
			//fis.mark(500);
			//特地在这里实验了这个mark reset。确实是无限循环
			while((length =fis.read(buffer,0,bufferLength))!= -1){
				fos.write(buffer, 0, length);
				//fis.reset();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 	}
}
