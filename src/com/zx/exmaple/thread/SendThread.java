package com.zx.exmaple.thread;

import com.zx.exmaple.baen.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 发送 消息的线程
 */
public class SendThread implements Runnable {

    private User user;

    public SendThread( User user) {
        this.user = user;
    }


    @Override
    public void run() {
        PrintStream out = null;
        try {
            out = new PrintStream(user.getSocket().getOutputStream(), true, "UTF-8");
            do {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                //如果 输入 exit， 则退出循环
                if ("exit".equals(message)) {
                    break;
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
