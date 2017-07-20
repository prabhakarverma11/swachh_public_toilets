package com.websystique.springmvc.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.http.HttpHeaders.USER_AGENT;

/**
 * Created by prabhakar on 24/6/17.
 */

public class UtilMethods {

    public JsonObject getJsonObjectFetchingURL(String url) throws IOException {
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        StringBuffer response = null;
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        Gson gson = new Gson();
//        System.out.println("Fetched url: " + url);
        if (response != null)
            return (JsonObject) gson.fromJson(response.toString(), JsonObject.class);
        else
            return null;
    }

    public Date formatStartDate(String startDate) throws ParseException {
        if (startDate != null && !startDate.equals("") && !startDate.equals("null"))
            return new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        else
            return null;
    }

    public Date formatEndDate(String endDate) throws ParseException {
        if (endDate != null && !endDate.equals("") && !endDate.equals("null"))
            return new Date(new SimpleDateFormat("dd-MM-yyyy").parse(endDate).getTime() + 24 * 60 * 60 * 1000 - 1000);
        else
            return null;
    }

    public Date formatStartDate(Date startDate) {
        Long millisADay = 24 * 60 * 60 * 1000L;
        if (startDate != null)
            return new Date(millisADay * ((startDate.getTime()) / millisADay));
        else
            return null;
    }

    public Date formatEndDate(Date endDate) {
        Long millisADay = 24 * 60 * 60 * 1000L;
        if (endDate != null)
            return new Date(millisADay * ((endDate.getTime()) / millisADay + 1) - 1000);
        else
            return null;
    }
}
