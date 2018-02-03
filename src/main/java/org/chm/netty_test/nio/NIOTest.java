package org.chm.netty_test.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering与Gathering
 */
public class NIOTest {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);
        int messageLength = 2+3+4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);
        SocketChannel socketChannel = serverSocketChannel.accept();


        while (true)
        {
            int bytesRead = 0;
            while (bytesRead < messageLength)
            {
                long r = socketChannel.read(buffers);
                bytesRead +=r;
                System.out.println("bytesRead:"+bytesRead);

                Arrays.asList(buffers).stream().map(buffer->"position:"+buffer.position()+",limit:"+buffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(buffer->buffer.flip());

            int bytesWrite = 0;

            while (bytesWrite < messageLength)
            {
                long w = socketChannel.write(buffers);
                bytesWrite += w;

            }

            Arrays.asList(buffers).forEach(buffer->buffer.clear());

            System.out.println("bytesRead:"+bytesRead+",byteWrite:"+bytesWrite);
        }

    }
}
