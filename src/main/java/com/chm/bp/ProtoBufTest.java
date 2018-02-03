package com.chm.bp;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by charming on 2017/6/8.
 */
public class ProtoBufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        PersonStudent.Student student = PersonStudent.Student.newBuilder().setName("zhangsan").setAge(22).setAddress("宁波").build();

        byte[] bytes = student.toByteArray();

        PersonStudent.Student student1 = PersonStudent.Student.parseFrom(bytes);
        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());

    }
}
