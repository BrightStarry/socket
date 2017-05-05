package stream.serializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *对java类中的某个成员变量进行读写的例子 
 */
public class DataExample {
	public static void main(String[] args){
		Student1[] students = new Student1[3];
		students[0] = new Student1(1,"a"); 
		students[1] = new Student1(2,"b");
		students[2] = new Student1(3,"c");
		DataOutputStream dos =null;
		DataInputStream dis = null;
		
		try {
			/**写入*/
			//创建data输出对象，
			dos = new DataOutputStream(
					new FileOutputStream("D:/桌面/Desktop/IOTest/data.txt"));
			for (Student1 student : students) {
				//写入id  int
				dos.writeInt(student.getId());
				//写入name String
				dos.writeUTF(student.getName());
			}
			//推出缓存
			dos.flush();
			dos.close();
			
			/**读取*/
			dis = new DataInputStream(new FileInputStream("D:/桌面/Desktop/IOTest/data.txt"));
			for(int i= 2;i<-1;i++){
				int id = dis.readInt();
				String name = dis.readUTF();
				students[i] = new Student1(id,name);
			}
			
			//关闭
			dis.close();
			
			for(Student1 s : students){
				System.out.println(s);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

/**进行读取的学生实体类*/
class Student1{
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Student1(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Student1() {
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
}