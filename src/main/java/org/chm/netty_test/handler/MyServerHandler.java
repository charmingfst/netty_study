package org.chm.netty_test.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created by charming on 2017/5/24.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":"+msg);
//        ctx.channel().writeAndFlush("from server:"+ UUID.randomUUID());
        ctx.channel().writeAndFlush(111L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
//        cause.printStackTrace();
        System.out.println("aaa");
        ctx.close();
    }
}
