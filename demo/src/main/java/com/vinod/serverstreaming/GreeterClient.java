package com.vinod.serverstreaming;


import com.vinod.demo.grpc.serverstream.generated.GreeterServiceGrpc;
import com.vinod.demo.grpc.serverstream.generated.HelloRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreeterClient {

    public static void main(String[] args) {
        // 1. Create a channel to connect to server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9091)  // Server address
                .usePlaintext()                  // No TLS/SSL (for development)
                .build();

        // 2. Create a blocking stub (synchronous calls)
        // GreeterServiceGrpc.GreeterServiceBlockingStub stub = 
        //         GreeterServiceGrpc.newBlockingStub(channel);

        GreeterServiceGrpc.GreeterServiceBlockingStub stub = GreeterServiceGrpc.newBlockingStub(channel);

        // 3. Create request
        HelloRequest request = HelloRequest.newBuilder()
                .setName("Vinodbhai")
                .build();

        System.out.println("📤 Sending request: " + request.getName());

        // 4. Make RPC call
        stub.sayHelloManyTimes(request).forEachRemaining(response -> {
            System.out.println("📥 Received response: " + response.getMessage());
        });

        // 5. Print response
       

        // 6. Shutdown channel
        channel.shutdown();
    }
}