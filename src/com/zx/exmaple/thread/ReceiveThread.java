package com.zx.exmaple.thread;

import com.zx.exmaple.baen.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 接收消息的线程
 */
public class ReceiveThread implements Runnable {

    private User user;

    private boolean flag = true;

    public ReceiveThread(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(user.getSocket().getInputStream(), "UTF-8");
            char[] chars = new char[100];
            while (flag) {
                int read = isr.read(chars);
                System.out.println(String.valueOf(chars));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
