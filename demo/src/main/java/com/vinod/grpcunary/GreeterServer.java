package com.vinod.grpcunary;


import com.vinod.demo.grpc.generated.GreeterServiceGrpc;
import com.vinod.demo.grpc.generated.HelloRequest;
import com.vinod.demo.grpc.generated.HelloResponse;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class GreeterServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create server on port 9090
        Server server = ServerBuilder.forPort(9090)
                .addService(new GreeterServiceImpl()) // Register your service implementation
                .build();

                
        // Start the server
        server.start();
        System.out.println("✅ Server started on port 9090");

        // Keep server running
        server.awaitTermination();
    }

    // Service implementation
    static class GreeterServiceImpl extends GreeterServiceGrpc.GreeterServiceImplBase {
        

        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            // 1. Extract data from request
            String clientName = request.getName();
            System.out.println("📨 Received request from: " + clientName);

            // 2. Create response
            HelloResponse response = HelloResponse.newBuilder()
                    .setMessage("Hello " + clientName + "! 👋")
                    .build();

            // 3. Send response back to client
            responseObserver.onNext(response);   // Send the response
            responseObserver.onCompleted();      // Mark RPC as completed
        }
    }
}