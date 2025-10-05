package com.vinod.grpcunary;

import com.vinod.demo.grpc.generated.GreeterServiceGrpc;
import com.vinod.demo.grpc.generated.HelloRequest;
import com.vinod.demo.grpc.generated.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreeterClient {

    public static void main(String[] args) {
        // 1. Create a channel to connect to server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)  // Server address
                .usePlaintext()                  // No TLS/SSL (for development)
                .build();

        // 2. Create a blocking stub (synchronous calls)
        GreeterServiceGrpc.GreeterServiceBlockingStub stub = 
                GreeterServiceGrpc.newBlockingStub(channel);

        // 3. Create request
        HelloRequest request = HelloRequest.newBuilder()
                .setName("Vinodbhai")
                .build();

        System.out.println("📤 Sending request: " + request.getName());

        // 4. Make RPC call
        HelloResponse response = stub.sayHello(request);

        // 5. Print response
        System.out.println("📥 Received response: " + response.getMessage());

        // 6. Shutdown channel
        channel.shutdown();
    }
}