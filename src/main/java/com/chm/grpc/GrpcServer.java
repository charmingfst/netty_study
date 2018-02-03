package com.chm.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created by charming on 2017/7/12.
 */
public class GrpcServer {

    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(50051).addService(new StudentServiceImpl()).build().start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("jvm关闭");
            GrpcServer.this.stop();

        }));
//        System.exit(0); windows系统需要加上这就才能看到回调钩子执行
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermination();
    }
}
