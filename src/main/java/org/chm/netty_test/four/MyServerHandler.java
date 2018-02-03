package org.chm.netty_test.four;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by charming on 2017/5/25.
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent)
        {
            IdleStateEvent event = (IdleStateEvent) evt;
            String state = null;
            switch (event.state())
            {
                case READER_IDLE:
                    state = "读空闲";
                    break;
                case WRITER_IDLE:
                    state="写空闲";
                    break;
                case ALL_IDLE:
                    state="读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+" 超时事件："+state);
            ctx.channel().close();
        }
    }
}
