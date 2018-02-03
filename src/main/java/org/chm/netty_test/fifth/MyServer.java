package org.chm.netty_test.fifth;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.chm.netty_test.four.MyServerInitializer;

/**
 * Created by charming on 2017/5/27.、
 * todo websocket
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup(); //获取连接，然后将任务交给workerGroup
        EventLoopGroup worker = new NioEventLoopGroup(); //执行任务操作

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //handler对于bossGroup, childHandler对应workerGroup
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new WebSocketChannelInitializer());

            ChannelFuture future = serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
