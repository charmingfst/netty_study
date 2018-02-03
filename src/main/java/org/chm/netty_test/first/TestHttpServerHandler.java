package org.chm.netty_test.first;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


/**
 * Created by charming on 2017/5/18.
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        ctx.channel().remoteAddress();

        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            req.method().name();

            Uri uri = new Uri(req.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求/favicon.ico");
                return;
            }

            ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            System.out.println("hello");
            ctx.writeAndFlush(response);
        }
    }
}
