package char_.file;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *FileReader  
 *PrintWriter
 *实例
 *
 */
public class FileExample {
	
	/**
	 *事实证明  FileReader或者FileWriter 都无法处理乱码问题 
	 */
	public static void main(String[] args) {
		FileReader fr = null;
		PrintWriter pw = null;
		
		try {
			fr = new FileReader("D:/桌面/Desktop/IOTest/6.txt");
			pw = new PrintWriter("UTF-8","D:/桌面/Desktop/IOTest/7.txt");
			
			int length = 0;
			char[] buffer = new char[3];
			int bufferLength = buffer.length;
			
			while((length = fr.read(buffer, 0, bufferLength)) != -1){
				pw.write(buffer,0,length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.close();
		}
	}

	
	/***
	 * 使用这个没办法处理乱码
	 */
	@SuppressWarnings("unused")
	private static void outByFileWriter() {
		//声明文件字符输入流
		FileReader fr = null;
		FileWriter fw = null;
		
		try {
			fr = new FileReader("D:/桌面/Desktop/IOTest/6.txt");
			fw = new FileWriter("D:/桌面/Desktop/IOTest/7.txt");
		
			char[] buffer = new char[3];
			int bufferLength = buffer.length;
			int length = 0;
			while((length = fr.read(buffer, 0, bufferLength)) != -1){
				fw.write(buffer,0,length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fr.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
