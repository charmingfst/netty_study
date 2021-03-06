package org.chm.netty_test.three;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by charming on 2017/5/25.
 * todo 多客户端连接和通讯
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if (channel != ch)
            {
                ch.writeAndFlush(channel.remoteAddress()+" 发送的消息："+ msg+ "\n");
            }else {
                ch.writeAndFlush(" 【自己】："+ msg+ "\n");
            }
        });

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        super.handlerAdded(ctx);
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+" 加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        super.handlerRemoved(ctx);
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】-"+channel.remoteAddress()+" 离开\n");
        //加不加这句都可以,会自动删
//        channelGroup.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" 上线");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        super.channelInactive(ctx);
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
