package com.spothero.grpc.server;

import com.spothero.grpc.CreateRequest;
import com.spothero.grpc.CreateResponse;
import com.spothero.grpc.Rate;
import com.spothero.grpc.RateServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.*;
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





        LocalDateTime from = LocalDateTime.ofInstant(Instant.parse( request.getFrom()) , ZoneOffset.UTC);

        LocalDateTime to = LocalDateTime.ofInstant(Instant.parse( request.getTo()) , ZoneOffset.UTC);



        String fromDayName = DayOfWeek.of(from.getDayOfWeek().getValue()).getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();

        String toDayName = DayOfWeek.of(to.getDayOfWeek().getValue()).getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();


        String fromH =String.format("%s%s", String.format("%02d", from.getHour()), String.format("%02d", from.getMinute()));

        String toH =String.format("%s%s", String.format("%02d", to.getHour()), String.format("%02d", to.getMinute()));

        Stream<Rate> f = list.stream().filter(r->
                Arrays.asList(r.getDays().split(",")).contains(fromDayName) == true
                && Arrays.asList(r.getDays().split(",")).contains(toDayName) == true
                && verifyIntervalOfTime(fromH,toH  , r)

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


    private boolean verifyIntervalOfTime(String from , String to , Rate r){
        int fromInt = Integer.parseInt(from);
        int toInt = Integer.parseInt(to);

        int rateFrom=Integer.parseInt(r.getTime().split("-")[0]);
        int rateTo=Integer.parseInt(r.getTime().split("-")[1]);

        boolean isBetween = fromInt > rateFrom && toInt < rateTo;
        return isBetween;
    }
}
