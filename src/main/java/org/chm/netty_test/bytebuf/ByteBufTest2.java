package org.chm.netty_test.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

/**
 * Created by charming on 2018/1/19.
 * 复合缓冲区
 */
public class ByteBufTest2 {
    public static void main(String[] args) {
        CompositeByteBuf byteBuf = Unpooled.compositeBuffer();

        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.directBuffer(10);

        byteBuf.addComponents(heapBuf, directBuf);
//        byteBuf.removeComponent(0);

        byteBuf.forEach(System.out::println);

        Iterator<ByteBuf> iterator = byteBuf.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }

    }
}
