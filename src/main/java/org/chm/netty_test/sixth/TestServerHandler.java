package org.chm.netty_test.sixth;

import com.chm.bp.PersonStudent;
import com.chm.pb.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by charming on 2017/6/14.
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.PersonType)
        {
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAddress());
        }else if (dataType == MyDataInfo.MyMessage.DataType.AnimalType)
        {
            MyDataInfo.Animal animal = msg.getAnimal();
            System.out.println(animal.getName());
            System.out.println(animal.getHeight());
        }else if (dataType == MyDataInfo.MyMessage.DataType.FlowerType)
        {
            MyDataInfo.Flower flower = msg.getFlower();
            System.out.println(flower.getName());
            System.out.println(flower.getColor());
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
