package org.chm.netty_test.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by charming on 2017/5/24.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        System.out.println("服务端接收到的消息内容："+new String(bytes, Charset.defaultCharset()));
        System.out.println("服务端接收到的消息数量："+(++this.count));

        ByteBuf responseByte = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        ctx.channel().writeAndFlush(responseByte);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        System.out.println("aaa");
        ctx.close();
    }
}
