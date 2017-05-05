package stream.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *文件输入流（读取）实例 
 */
public class FileInputStreamExample {
	public static void main(String[] args) throws Exception{
		System.out.println("文件大小：" + getFileLength() +"B");
	}
	
	/**
	 * 获取文件长度
	 * 没有使用缓冲区的读取
	 */
	public static int getFileLength() {
		int count = 0;
		//输入流
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(new File("D:" + File.separator + "桌面" 
					+ File.separator + "Desktop" + File.separator + "IOTest" + File.separator
					+ "1.jpg"));
			while(inputStream.read() != -1){//循环读取文件字节
				count++;
			}
			inputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
}
