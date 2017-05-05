package stream.file;

import java.io.File;

/**
 *对 file 的io 实例
 */
public class FileExample {
	
	public static void main(String[] args) throws Exception{
		createFile();
	}
	
	/**
	 * 文件处理 实例
	 * @throws Exception 
	 */
	public static void createFile() throws Exception{
		File file = new File("D:" + File.separator 
				+ "桌面" + File.separator + "Desktop" + File.separator + "IOTest"
				+ File.separator + "1.txt");
		//当路径下没有该文件时，创建一个新的空文件，如果文件夹不对，也就是路径不对，则抛出异常
		file.createNewFile();
		System.out.println("该分区大小：" + file.getTotalSpace() );//返回的是该磁盘的分区，返回的是BYTE
		System.out.println("该分区大小:" + file.getTotalSpace()/(1024*1024*1024) + "G");
		System.out.println("文件父目录：" + file.getParent());
		
		
		
		
	}
}
