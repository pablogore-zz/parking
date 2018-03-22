package com.spothero.api;

import com.spothero.grpc.CreateRequest;
import com.spothero.grpc.CreateResponse;
import com.spothero.grpc.RateServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

    public static CreateResponse Calculate(String from , String to) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9001)
            .usePlaintext(true)
            .build();

        RateServiceGrpc.RateServiceBlockingStub stub1
                = RateServiceGrpc.newBlockingStub(channel);

        CreateResponse createResponse = stub1.calculate(CreateRequest.newBuilder()
                .setFrom(from)
                .setTo(to)
                .build());

        System.out.println("Response received from server:\n" + createResponse);

        channel.shutdown();

        return createResponse;
    }
}
