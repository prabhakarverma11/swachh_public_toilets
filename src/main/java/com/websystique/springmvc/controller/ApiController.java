package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.model.Report;
import com.websystique.springmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    LocationService locationService;

    @Autowired
    PlaceULBMapService placeULBMapService;

    @Autowired
    ReportService reportService;

    @Autowired
    PlaceDetailService placeDetailService;

    @Autowired
    PlaceService placeService;

    @Autowired
    PinCodeDetailService pinCodeDetailService;

    @ResponseBody
    @RequestMapping(value = "/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void getReportOfLocationsByLocationTypeRatingRangeAndBetweenDateByPageAndSize(@RequestParam(required = false) String locationType, @PathVariable Double ratingFrom, @PathVariable Double ratingEnd, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @PathVariable Integer page, @PathVariable Integer size, HttpServletRequest request, HttpServletResponse response) {
        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO

        List<PlaceDetail> placeDetails = placeDetailService.getAllPlaceDetailsByLocationTypeRatingRangePageAndSize(locationType, ratingFrom, ratingEnd, page, size);

        List<Report> reports = reportService.getReportsListByPlaceDetailsRatingRangeBetweenDates(placeDetails, ratingFrom, ratingEnd, startDate, endDate);

        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", gson.toJson(reports));
            jsonObject.addProperty("locationType", locationType);
            jsonObject.addProperty("ratingFrom", ratingFrom);
            jsonObject.addProperty("ratingEnd", ratingEnd);
            jsonObject.addProperty("startDate", startDate);
            jsonObject.addProperty("endDate", endDate);
            jsonObject.addProperty("page", page);
            jsonObject.addProperty("size", size);
            Long endTime = System.currentTimeMillis();
            jsonObject.addProperty("timeTaken", (endTime - startTime) / 1000);

            writer.print(jsonObject);
            writer.flush();
            response.setStatus(200);
        } catch (IOException e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }
}