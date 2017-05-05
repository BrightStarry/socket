package stream.pushback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

/**
 *回退 字节 IO流  实例 
 *这个只有一个 InputStream 
 */
public class PushbackExample {
	public static void main(String[] args){
		String str = "hello,world!";
		//声明数组字节流对象
		ByteArrayInputStream bais = null;
		//声明回退流对象
		PushbackInputStream pis = null;
		
		try {
			//从str转换的byte[]中读取
			bais = new ByteArrayInputStream(str.getBytes());
			pis = new PushbackInputStream(bais);
			
			@SuppressWarnings("unused")
			int temp = 0;//统计每次读取到的字节
			
			
			
			/**
			 * 这个东西如果两个两个字节读取就可以替换一些中文字符了
			 * 这个流和其他流不同的地方也就是在于，read()方法返回的
			 * 不是读取的字节数，而是读取到的内容
			 * 其本质是读取到下一个读取的东西后回退，
			 * 需要注意的是，如果要插入，插入的不能超过缓冲区的大小
			 */
			//逐字读取字节存放在temp中
			while((temp = pis.read()) != -1){
				/**
				 * 把 , 替换成 *
				 */
//				if(temp == ','){
//					//如果读取的是逗号
//					temp = pis.read();//读取下一个字节
//					System.out.print("*" + (char)temp );
//				}else{
//					System.out.print((char)temp);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bais.close();
				pis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
