package com.zx.exmaple.point;

import com.zx.exmaple.baen.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务端
 */
public class Server {

    private static List<User> users = new ArrayList<>();

    private static AtomicInteger count = new AtomicInteger(0);//统计所有登录过的人数，防止id重复

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务开启");
            Socket socket = null;
            //永久等待别人的连接
            while(true){
                socket = serverSocket.accept();
                //当收到连接，新建user，里面有线程
                User user = new User(count.incrementAndGet(),socket);
                addUser(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取在线人数
     */
    public synchronized static int getSize(){
        return users.size();
    }

    /**
     * 上线
     */
    public  static void addUser(User user){
        users.add(user);
    }

    /**
     * 下线
     */
    public synchronized static void removeUser(Integer id){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == id){
                users.remove(i);
                break;
            }
        }
    }

}
