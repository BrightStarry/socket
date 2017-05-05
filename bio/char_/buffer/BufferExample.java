package char_.buffer;

import java.io.*;

/**
 *Buffer 字符io 实例 
 *
 */
public class BufferExample {
	public static void main(String[] args) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			/**
			 *bufferReader似乎只有包装 InputStreamReader这个类才能设定编码
			 */
			br = new BufferedReader(new InputStreamReader(new FileInputStream("D:/桌面/Desktop/111.txt"), "UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/桌面/Desktop/222.txt")));
			
			String line = null;
			while((line =br.readLine()) != null){
				bw.write(line);
				bw.newLine();//应该是在文件中指针指到下一行,  对，不加这个，全都在一行了
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
