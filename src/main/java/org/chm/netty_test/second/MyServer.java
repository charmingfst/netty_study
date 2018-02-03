package org.chm.netty_test.second;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.chm.netty_test.second.MyServerInitializer;

import static jdk.nashorn.internal.objects.NativeFunction.bind;

/**
 * Created by charming on 2017/5/24.
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new MyServerInitializer());

            /**
             * sync()方法等待端口绑定等一系列操作完成，确保完成才能继续往下走
             */
            ChannelFuture future = serverBootstrap.bind(8899).sync();
            //表示channel关闭了才会往下走，这里会一直等待
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
