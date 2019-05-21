package com.example.grpcs.proto;

import com.example.grpcs.GreeterGrpc;
import com.example.grpcs.HelloWorldProto;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HelloWorldServer {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldServer.class.getName());

    private Server server;

    public void start(int port) throws IOException {
    /* The port on which the server should run */
       // int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start(50051);
        server.blockUntilShutdown();
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {


        @Override
        public void sayHello(HelloWorldProto.HelloRequest request, StreamObserver<HelloWorldProto.HelloReply> responseObserver) {
            HelloWorldProto.HelloReply reply = HelloWorldProto.HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void sayHelloAgain(HelloWorldProto.HelloRequest req, StreamObserver<HelloWorldProto.HelloReply> responseObserver) {
            HelloWorldProto.HelloReply reply = HelloWorldProto.HelloReply.newBuilder().setMessage("Hello again " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}