package com.xiaolong.newio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {

        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();
        // 第一个线程
        new Thread(() -> {

            try {
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();

                listenerChannel.socket().bind(new InetSocketAddress(8000));
                listenerChannel.configureBlocking(false);
                listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while (true) {
                    System.out.println("监控一次");
                    if  (serverSelector.select(1) > 0){
                        System.out.println("有服务到来");
                        Set<SelectionKey> set = serverSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = set.iterator();

                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();

                            if (key.isAcceptable()){
                                try {
                                    SelectableChannel channel = key.channel();
                                    ServerSocketChannel channel1 = (ServerSocketChannel) channel;
                                    SocketChannel clientChannel = channel1.accept();

                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "server thread").start();


        new Thread(() -> {
            while (true) {
                try {
                    if (clientSelector.select(1) > 0){
                        Iterator<SelectionKey> keyIterator = clientSelector.selectedKeys().iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey selectionKey = keyIterator.next();

                            if  (selectionKey.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                                    ByteBuffer allocate = ByteBuffer.allocate(1024);

                                    // 面向buffer编程
                                    clientChannel.read(allocate);
                                    allocate.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(allocate) + Thread.currentThread().getName());
                                } finally {
                                    keyIterator.remove();
                                    selectionKey.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "client thread").start();
    }


}
