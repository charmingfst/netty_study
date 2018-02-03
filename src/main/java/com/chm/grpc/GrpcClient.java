package com.chm.grpc;

import com.chm.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * Created by charming on 2017/7/12.
 */
public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);

                MyResponse myResponse = blockingStub.getRealNameByUserName(MyRequest.newBuilder().setName("zhangsan").build());
        System.out.println(myResponse.getRealname());
        myResponse = blockingStub.getRealNameByUserName(MyRequest.newBuilder().setName("zhangsan").build());
        System.out.println(myResponse.getRealname());
        myResponse = blockingStub.getRealNameByUserName(MyRequest.newBuilder().setName("zhangsan").build());
        System.out.println(myResponse.getRealname());

       Iterator<StudentResponse> studentResponseIterator =  blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(28).build());
        System.out.println("--------------------");
        while (studentResponseIterator.hasNext())
        {
            StudentResponse response = studentResponseIterator.next();
            System.out.println(response.getName());
        }

        managedChannel.shutdownNow();
    }

}
