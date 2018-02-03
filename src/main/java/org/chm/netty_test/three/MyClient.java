package org.chm.netty_test.three;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by charming on 2017/5/25.
 */
public class MyClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());
            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true)
            {
                channel.writeAndFlush(reader.readLine()+"\r\n");
            }
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
