package com.chm.grpc;

import com.chm.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

import static com.chm.proto.StudentServiceGrpc.newStub;

/**
 * Created by charming on 2017/7/15.
 */
public class GrpcClientAsync {
    public static void main(String[] args) {


        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
        //只要客户端以流式的方式向服务端发送请求，这种请求都是异步的。
        StudentServiceGrpc.StudentServiceStub asyncStub = StudentServiceGrpc.newStub(managedChannel);

        StreamObserver<StudentResponseList> responseObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach((studentResponse)->{
                    System.out.println(studentResponse.getName());
                    System.out.println(studentResponse.getAge());
                    System.out.println(studentResponse.getCity());
                    System.out.println("*******************");
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };
        StreamObserver<StudentRequest> requestObserver = asyncStub.getStudentsWrapperByAges(responseObserver);
        requestObserver.onNext(StudentRequest.newBuilder().setAge(10).build());
        requestObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        requestObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        requestObserver.onCompleted();


        StreamObserver<StreamRequest> requestStreamObserver = asyncStub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        });

        for (int i = 0; i < 10; i++)
        {
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        managedChannel.shutdownNow();
    }
}
