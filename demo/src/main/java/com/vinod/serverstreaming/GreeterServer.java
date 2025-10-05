package com.vinod.serverstreaming;




import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

import com.vinod.demo.grpc.serverstream.generated.GreeterServiceGrpc;
import com.vinod.demo.grpc.serverstream.generated.HelloRequest;
import com.vinod.demo.grpc.serverstream.generated.HelloResponse;

public class GreeterServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create server on port 9090
        Server server = ServerBuilder.forPort(9091)
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
        public void sayHelloManyTimes(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            // 1. Extract data from request
            String clientName = request.getName();
            System.out.println("📨 Received request from: " + clientName);

            // 2. Create response
         

            // 3. Send response back to client
            for(int i=0;i<=100;i++){
                   HelloResponse response = HelloResponse.newBuilder()
                    .setMessage("Hello " +i+ clientName + "! 👋")
                    .build();
    responseObserver.onNext(response);   // Send the response
            }
        
            responseObserver.onCompleted();      // Mark RPC as completed
        }
    }
}