package org.chm.netty_test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by charming on 2017/12/26.
 */
public class NIOServer {

    private static HashMap<String, SocketChannel> channelHashMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //返回当前关注的事件个数
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();


            selectionKeys.forEach(selectionKey ->
            {
                selectionKey.selector().wakeup();
                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();

                        SocketChannel socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        String key = "[" + UUID.randomUUID().toString() + "]";

                        channelHashMap.put(key, socketChannel);

                    } else if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(byteBuffer);
                        if (count > 0) {
                            byteBuffer.flip();
                            Charset charset = Charset.forName("utf-8");
//                            System.out.println(new String(byteBuffer.array()));
                            String message = String.valueOf(charset.decode(byteBuffer).array());
                            System.out.println("消息：" + message);

                            String senderKey = null;

                            for (Map.Entry<String, SocketChannel> entry : channelHashMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    senderKey = entry.getKey();
                                    break;
                                }
                            }

                            for (Map.Entry<String, SocketChannel> entry : channelHashMap.entrySet()) {
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((senderKey + ":" + message).getBytes());
                                writeBuffer.flip();
                                SocketChannel value = entry.getValue();
                                value.write(writeBuffer);
                            }

                        }

                    }
                } catch (IOException ex) {

                    ex.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            selectionKeys.clear();
        }
    }
}
