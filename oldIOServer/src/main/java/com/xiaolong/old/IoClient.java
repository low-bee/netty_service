package com.xiaolong.old;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class IoClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    socket.getOutputStream().write((new Date() + ": hello world").getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(1000 );
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
