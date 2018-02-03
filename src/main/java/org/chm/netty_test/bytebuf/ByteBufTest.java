package org.chm.netty_test.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by charming on 2018/1/19.
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("张hello world", Charset.forName("utf-8"));

        if (byteBuf.hasArray())
        {
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes, Charset.defaultCharset()));

            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char) byteBuf.getByte(i));
            }


            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());
            int num = byteBuf.readableBytes();

            for (int i = 0; i < num; i++) {
                System.out.println((char) byteBuf.readByte());
            }

            System.out.println(byteBuf.getCharSequence(0, 3, Charset.forName("utf-8")));
        }


    }
}
