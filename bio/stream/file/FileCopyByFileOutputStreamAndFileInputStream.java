package stream.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *使用文件字节IO流进行文件拷贝 
 */
public class FileCopyByFileOutputStreamAndFileInputStream {
	public static void main(String[] args){
		fileCopy();
	}
	
	/**
	 * FileCopy
	 */
	public static void fileCopy(){
		//缓冲区对象
		byte[] buffer = new byte[512];
		int numberRead = 0;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream(new File("D:"+File.separator+"桌面"+File.separator
					+"Desktop"+File.separator+"IOTest"+File.separator+"1.jpg"));
			fos = new FileOutputStream(new File("D:"+File.separator+"桌面"+File.separator
					+"Desktop"+File.separator+"IOTest"+File.separator+"2.jpg"));//如果文件不存在会自动创建
			//numberRead的作用在于防止最后一次读取的字节小于buffer的长度
			//也就是说，read方法返回的是每次读取到的字节的大小
			while((numberRead = fis.read(buffer)) != -1){
				//arg0:缓冲区，arg1:偏移量,arg2:写入长度
				fos.write(buffer,0,numberRead);
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
