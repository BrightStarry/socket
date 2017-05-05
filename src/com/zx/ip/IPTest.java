package com.zx.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 测试本网段有多少可用ip
 */
public class IPTest {
    /**
     * 获取本地ip
     */
    public static String getLocalIp() {
        InetAddress host = null;
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = host.getHostAddress();
        return ip;
    }

    /**
     * 发送ping
     */
    public static boolean ping(String ip){
        try {
            Process process = Runtime.getRuntime().exec("ping " + ip);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null){
                if(line.length() != 0){
                    stringBuilder.append(line + "\r\n");
                }
            }
            if(!stringBuilder.toString().contains("100% 丢失")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 测试当前网段有多少可用ip
     */
    public static void main(String[] args){
        String ip = getLocalIp();
        String preIp = ip.substring(0,ip.lastIndexOf('.'));
        for(int i = 1 ; i <= 255; i++){
            String tempIp = preIp + "." + i;
            System.out.println(tempIp);
            System.out.println(ping(tempIp));
            System.out.println();
        }

    }
}
