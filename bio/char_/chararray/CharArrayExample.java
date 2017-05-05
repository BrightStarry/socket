package char_.chararray;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;

/**
 *字符数组 IO流 实例 
 */
public class CharArrayExample {
	public static void main(String[] args) {
		String str = "以大多数人的努力程度之低，都轮不到拼天赋";
		
		//声明字符数组输入流对象
		CharArrayReader car = null;
		//声明字符数组输出流对象
		CharArrayWriter caw = null;
		
		try {
			car = new CharArrayReader(str.toCharArray());
			caw = new CharArrayWriter();
			
			
			int length = 0;
			char[] buffer = new char[3];
			int bufferLength = buffer.length;
			while((length = car.read(buffer,0,bufferLength))!= -1){
				caw.write(buffer,0,length);
			}
			/**
			 * 这个CharArrayWriter是不需要给他一个char[]参数的，他本身就有一个
			 * char[],会自动把字符写入这个成员变量，并且自动扩容
			 */
			System.out.println(caw);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			car.close();
			caw.close();
		}
		
	}
}
