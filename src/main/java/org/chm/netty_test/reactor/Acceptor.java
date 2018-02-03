package org.chm.netty_test.reactor;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by charming on 2018/1/5.
 */
public class Acceptor implements Runnable {
    private ServerSocketChannel serverSocket;

    public Acceptor(ServerSocketChannel serverSocketChannel) {
        this.serverSocket = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel c = serverSocket.accept();
            if (c != null)
            {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
