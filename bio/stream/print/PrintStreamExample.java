package stream.print;

import java.io.File;
import java.io.PrintStream;

/**
 *PrintStream 类  
 *继承与FileOutStream类 
 *是用来装饰（就是之前的包裹，应该用装饰这个词更为恰当，还没看装饰者模式）其他输出流的
 *它永远不会抛出IOException,它产生的IOException会被自身捕获，并产生错误标记，
 *可以通过checkError()方法返回错误标记，从而查看是否产生了IOException.
 *PrintWrite还提供了自动flush()和字符集设置功能
 */
public class PrintStreamExample {
	public static void main(String[] args) throws Exception {
		 byte[] arr={0x61, 0x62, 0x63, 0x64, 0x65 };//abcde
		
		File file = new File("D:/桌面/Desktop/IOTest/5.txt");
		@SuppressWarnings("resource")
		PrintStream ps1 = new PrintStream(file);
		ps1.println("郑星");
		ps1.write(arr);
	}
}
