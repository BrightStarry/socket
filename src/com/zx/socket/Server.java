package com.zx.socket;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * socket
 * 服务端
 */
public class Server {

    public static void main(String[] args) {
        try {
            //创建socket服务 ，端口号 8888
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务开启，监听 8888 端口");
            //等待别人的连接
            Socket socket = serverSocket.accept();
            System.out.println("收到连接");

            InputStream in = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");

            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream, true, "UTF-8");
            char[] chars = new char[1024];


            String temp;
            do {
                Scanner scanner = new Scanner(System.in);
                printStream.print(scanner.nextLine());
                int read = isr.read(chars);
                temp = String.valueOf(chars).trim();
                System.out.println(temp);
            } while (!"exit".equals(temp));

            isr.close();
            in.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
