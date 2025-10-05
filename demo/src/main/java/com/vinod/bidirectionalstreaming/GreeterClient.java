package com.vinod.bidirectionalstreaming;



import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.vinod.demo.grpc.bidirectionalstream.generated.GreeterServiceGrpc;
import com.vinod.demo.grpc.bidirectionalstream.generated.HelloRequest;
import com.vinod.demo.grpc.bidirectionalstream.generated.HelloResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

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
        CountDownLatch latch = new CountDownLatch(1);

        GreeterServiceGrpc.GreeterServiceStub stub = GreeterServiceGrpc.newStub(channel);

        StreamObserver<HelloRequest> requestObserver = stub.sayHelloToEveryone(new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse value) {
                System.out.println("📥 Received response: " + value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("✅ Server completed sending responses");
                latch.countDown();
            }
        });

        // 3. Create request
      for (int i = 1; i <= 500; i++) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            String name = "Vinodbhai " + i;
            HelloRequest request = HelloRequest.newBuilder()
                    .setName(name)
                    .build();

            System.out.println("📤 Sending request: " + request.getName());
            requestObserver.onNext(request); // Send each request
        }

        // 5. Print response

        requestObserver.onCompleted();
        try {
            latch.await(3,TimeUnit.SECONDS); // Wait for the server to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       

        // 6. Shutdown channel
      
    }
}