package com.spothero.grpc.server;

import com.spothero.grpc.CreateRequest;
import com.spothero.grpc.CreateResponse;
import com.spothero.grpc.Rate;
import com.spothero.grpc.RateServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Stream;

public class RateServiceImpl extends RateServiceGrpc.RateServiceImplBase {
    @Override
    public void calculate(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);


        Config config = new Config();
        String path  = config.getProperty("path");
        System.out.println(path);

        List<Rate> list = ReadJson.readJson(path);


        Instant from = Instant.parse( request.getFrom() );
        ZonedDateTime fromZone = from.atZone(ZoneId.of("America/Montreal"));

        String.format("%04d", fromZone.getHour());


        Instant to = Instant.parse( request.getTo() );
        ZonedDateTime toZone = to.atZone(ZoneId.of("America/Montreal"));

        String.format("%04d", toZone.getHour());

        String fromDayName = DayOfWeek.of(fromZone.getDayOfWeek().getValue()).getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();

        String toDayName = DayOfWeek.of(toZone.getDayOfWeek().getValue()).getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();


        Stream<Rate> f = list.stream().filter(r->
                Arrays.asList(r.getDays().split(",")).contains(fromDayName) == true
                && Arrays.asList(r.getDays().split(",")).contains(toDayName) == true
        );


        Iterator<?> it = Arrays.asList(f.toArray()).iterator();
        List<Rate> rates = new ArrayList<>();

        while (it.hasNext()){
            Rate r = (Rate) it.next();
            rates.add(r);
        }


        CreateResponse response = CreateResponse.newBuilder()
                .addAllRate(rates)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }
}
