package org.chm.netty_test.handler3;

import com.google.common.base.Charsets;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * Created by charming on 2018/1/25.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "send to server";
        for (int i = 0; i < 10; i++) {
            msg=msg.concat(String.valueOf(i));
            int length = msg.getBytes("utf-8").length;
            byte[] content = msg.getBytes("utf-8");
            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setLength(length);
            personProtocol.setContent(content);

            ctx.writeAndFlush(personProtocol);

        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] bytes = msg.getContent();

        System.out.println("接收到服务端消息内容："+new String(bytes, Charset.defaultCharset()));
        System.out.println("接收到服务端消息内容："+(++this.count));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
