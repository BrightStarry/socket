package stream.serializable;

import java.io.*;

/**
 *对对象读取的实例  ，对象指的就是class，
 *注意，被读取的对象必须实现 serializable接口
 */
public class ObjectExample {
	public static void main(String[] args){
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(
					new FileOutputStream(
							new File("D:"+File.separator+"桌面"+File.separator+"Desktop"
									+File.separator+"IOTest"+File.separator
									+"class.txt")));
			oos.writeObject(new Student(1,"郑星"));
			oos.writeObject(new Student(2,"智障亏"));
			oos.writeObject(new Student(3,"傻逼胖子"));
			
			ois = new ObjectInputStream(new FileInputStream(
					"D:"+File.separator+"桌面"+File.separator+"Desktop"+File.separator+"IOTest"
							+File.separator+"class.txt"));
			
//			Object object = null;
//			while((object =ois.readObject())!=null){
//				System.out.println((Student)object);
//			}
			for(int i =0;i < 3;i++){
				System.out.println(ois.readObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				oos.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}

/**
 *测试用的学生类 
 */
class Student implements Serializable{
	private static final long serialVersionUID = 1L;
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
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
	public Student(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}