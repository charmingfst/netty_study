package org.chm.netty_test.sixth;

import com.chm.bp.PersonStudent;
import com.chm.pb.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.base64.Base64;

import java.util.Random;

/**
 * Created by charming on 2017/6/14.
 */
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
//        PersonStudent.Student student = PersonStudent.Student.newBuilder().
//                setName("zhangsan").setAge(22).setAddress("addr").build();
//        ctx.channel().writeAndFlush(student);
        MyDataInfo.MyMessage message = null;

        int num = new Random().nextInt(3);
        if (num == 0)
        {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType).
                    setPerson(MyDataInfo.Person.newBuilder().setName("zhangsan").setAge(23).setAddress("位置").build()).build();
        }else if (num == 1)
        {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.AnimalType).
                    setAnimal(MyDataInfo.Animal.newBuilder().setName("dog").setHeight(20).build()).build();
        }else if (num == 2)
        {
            message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.FlowerType).
                    setFlower(MyDataInfo.Flower.newBuilder().setName("rose").setColor("red").build()).build();
        }
        ctx.channel().writeAndFlush(message);
    }
}
