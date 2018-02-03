package org.chm.netty_test.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by charming on 2017/12/26.
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8899));

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isConnectable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    if (client.isConnectionPending()) {
                        client.finishConnect();

                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

                        writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());

                        writeBuffer.flip();

                        client.write(writeBuffer);


                        ExecutorService service = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                        service.submit(() -> {
                            while (true) {
                                try {
                                    writeBuffer.clear();
                                    InputStreamReader input = new InputStreamReader(System.in);
                                    BufferedReader reader = new BufferedReader(input);

                                    String content = reader.readLine();
//                                    System.out.println("content:" + content);
                                    writeBuffer.put(content.getBytes());
                                    writeBuffer.flip();
                                    client.write(writeBuffer);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        client.register(selector, SelectionKey.OP_READ);

                    }
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel1 = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel1.read(byteBuffer);
                    System.out.println(new String(byteBuffer.array()));

                }
            }
            selectionKeys.clear();

        }

    }
}
