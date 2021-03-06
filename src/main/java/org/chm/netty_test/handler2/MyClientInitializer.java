package org.chm.netty_test.handler2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.chm.netty_test.handler.MyByteToLongDecoder;
import org.chm.netty_test.handler.MyLongToByteEncoder;


/**
 * Created by charming on 2017/5/24.
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyClientHandler());
    }
}
