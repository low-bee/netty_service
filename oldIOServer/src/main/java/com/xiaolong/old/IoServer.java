package com.xiaolong.old;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);

        new Thread(() -> {
            while (true) {


                try {
                    // 阻塞
                    System.out.println("正在阻塞中..........");
                    Socket socket = serverSocket.accept();
                    System.out.println("解阻塞，socket: " + socket.getClass());
                    int[] num = {0};
                    new Thread(() -> {
                        int len;
                        byte[] bytes = new byte[1024];
                        try(InputStream inputStream = socket.getInputStream()) {

                            while ((len = inputStream.read(bytes)) != 1){
                                System.out.println(new String(bytes, 0, len) + Thread.currentThread().getName());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }, "process thread - " + num[0]++).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "accept thread - 1").start();
    }
}
