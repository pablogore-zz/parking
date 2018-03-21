package com.spothero.grpc.server;


import com.spothero.grpc.Rate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ReadJson {

    /**
     * read a json file
     * @return
     */
    public static List<Rate> readJson(String config)  {

        JSONParser parser = new JSONParser();
        List<Rate> list = new ArrayList<>();

        try {

            Object obj = parser.parse(new FileReader(config + "/sample.json"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);


            JSONArray ratesArray = (JSONArray) jsonObject.get("rates");
            Iterator it  = ratesArray.iterator();
            while (it.hasNext()){
                org.json.simple.JSONObject object   = (org.json.simple.JSONObject) it.next();

                Rate rate = Rate.newBuilder()
                        .setTime(object.getOrDefault("times","").toString())
                        .setDays(object.getOrDefault("days","").toString())
                        .setPrice(Float.valueOf(object.getOrDefault("price",0).toString()))
                        .build();
                list.add(rate);
            }

            return list;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    private void configuration(){

        Properties configFile;


    }
}
