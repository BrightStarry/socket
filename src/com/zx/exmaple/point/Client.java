package com.zx.exmaple.point;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 */
public class Client {

    public static void main(String[] args) {
        try {
            //连接本机的8888端口
            Socket socket = new Socket("127.0.0.1", 8888);
            System.out.println(socket);

            InputStream inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream, true, "UTF-8");
            char[] chars = new char[1024];
            String temp;
            do{
                Scanner scanner = new Scanner(System.in);
                temp = scanner.nextLine();
                printStream.print(temp);
            }while(!"exit".equals(temp));

            printStream.close();
            outputStream.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
