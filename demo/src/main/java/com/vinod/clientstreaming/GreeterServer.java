package com.vinod.clientstreaming;




import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;



import java.io.IOException;

import com.vinod.demo.grpc.clientstream.generated.GreeterServiceGrpc;
import com.vinod.demo.grpc.clientstream.generated.HelloRequest;
import com.vinod.demo.grpc.clientstream.generated.HelloResponse;



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
        public   StreamObserver<HelloRequest> sayLongHello ( StreamObserver<HelloResponse> responseObserver) {
         StringBuilder sb = new StringBuilder();    // Mark RPC as completed
         return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest request) {
                // 1. Extract data from request
                String clientName = request.getName();
                System.out.println("📨 Received request from: " + clientName);
                sb.append(clientName).append(" ");
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                // 2. Create response
                HelloResponse response = HelloResponse.newBuilder()
                        .setMessage("Hello " + sb.toString() + "! 👋")
                        .build();

                // 3. Send response back to client
                responseObserver.onNext(response);   // Send the response
                responseObserver.onCompleted();      // Mark RPC as completed
            }
        };
    }
}
}