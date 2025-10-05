package com.vinod.bidirectionalstreaming;




import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;



import java.io.IOException;

import com.vinod.demo.grpc.bidirectionalstream.generated.GreeterServiceGrpc;
import com.vinod.demo.grpc.bidirectionalstream.generated.HelloRequest;
import com.vinod.demo.grpc.bidirectionalstream.generated.HelloResponse;





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
        public   StreamObserver<HelloRequest> sayHelloToEveryone ( StreamObserver<HelloResponse> responseObserver) {
         StringBuilder sb = new StringBuilder();    // Mark RPC as completed
         return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest request) {
                for(int i=0;i<=10;i++){
                responseObserver.onNext(HelloResponse.newBuilder().setMessage("Hello " + request.getName() + "! 👋" +i).build());

                }
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
             

                // 3. Send response back to client
             // Send the response
                responseObserver.onCompleted();      // Mark RPC as completed
            }
        };
    }
}
}