package com.zx.exmaple.baen;

import com.zx.exmaple.thread.ReceiveThread;
import com.zx.exmaple.thread.SendThread;

import java.net.Socket;

/**
 * 连接到 server 的用户
 */
public class User {

    private Socket socket;
    private Thread sendThrad;//用户的发送线程
    private Thread receiveThread;//接收线程
    private Integer id;

    public User(Integer id,Socket socket){
        this.id = id;
        this.socket = socket;
        this.sendThrad = new Thread(new SendThread(this));
        this.receiveThread = new Thread(new ReceiveThread(this));
        sendThrad.start();
        receiveThread.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
