package com.miracle.overwatch.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author QiuKai
 * @date 2021/10/1 2:33 下午
 */
@Slf4j
public class NetUtil {

    private static int port = 7359;
    public static int getPort(){
//        int initPort = 7359;
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            if(!isPortUsing(initPort)){
//                break;
//            }
//            initPort++;
//        }
        port++;
        log.info("端口号为：{}", port);
        return port;
    }
    public static boolean isPortUsing(int port) {
        boolean flag = false;
        try {
            // 如果报异常，则说明该端口未被使用
            Socket socket = new Socket("127.0.0.1", port);
            socket.close();
            flag = true;
        } catch (IOException e) {

        }
        return flag;
    }

    public static String getHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getPort());
        }
    }
}
