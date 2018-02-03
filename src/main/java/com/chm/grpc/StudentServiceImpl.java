package com.chm.grpc;

import com.chm.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

/**
 * Created by charming on 2017/7/12.
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
//        super.getRealNameByUserName(request, responseObserver);
        System.out.println("来自客户端信息：" + request.getName());
        MyResponse response = MyResponse.newBuilder().setRealname("张三").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
//      super.getStudentsByAge(request, responseObserver);
        System.out.println("来自客户端信息：" + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(22).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(32).setCity("天津").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(42).setCity("上海").build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
//        return super.getStudentsWrapperByAges(responseObserver);
        return new StreamObserver<StudentRequest>() {

            @Override
            public void onNext(StudentRequest value) {
                System.out.println("客户端信息：" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse response1 = StudentResponse.newBuilder().setName("张三").setAge(22).setCity("杭州").build();
                StudentResponse response2 = StudentResponse.newBuilder().setName("李四").setAge(23).setCity("广州").build();
                StudentResponseList responseList = StudentResponseList.newBuilder().addStudentResponse(response1).addStudentResponse(response2).build();
                responseObserver.onNext(responseList);
                responseObserver.onCompleted();
            }
        };

    }

    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
//        return super.biTalk(responseObserver);
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println(value.getRequestInfo());
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();

            }
        };
    }
}
