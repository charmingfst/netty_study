package org.chm.netty_test.handler3;

import com.google.common.base.Charsets;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by charming on 2018/1/25.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] bytes = msg.getContent();

        System.out.println("服务端接收到的消息内容："+new String(bytes, Charset.defaultCharset()));
        System.out.println("服务端接收到的消息数量："+(++this.count));

        String uuid = UUID.randomUUID().toString();

        int idLength = uuid.getBytes("utf-8").length;
        byte[] idContent = uuid.getBytes("utf-8");

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(idLength);
        personProtocol.setContent(idContent);

        ctx.writeAndFlush(personProtocol);
    }
}
