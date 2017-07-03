package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    ReviewService reviewService;

    @Autowired
    PlaceService placeService;

    @Autowired
    PinCodeDetailService pinCodeDetailService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void getReportOfLocationsByLocationTypeRatingRangeAndBetweenDateByPageAndSize(@RequestParam(required = false) String locationType, @PathVariable Double ratingFrom, @PathVariable Double ratingEnd, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, @PathVariable Integer page, @PathVariable Integer size, HttpServletRequest request, HttpServletResponse response) {
        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO

        List<PlaceDetail> placeDetails = placeDetailService.getAllPlaceDetailsByLocationTypeRatingRangePageAndSize(locationType, ratingFrom, ratingEnd, page, size);

        List<Report> reports = reportService.getReportsListByPlaceDetailsBetweenDates(placeDetails, startDate, endDate);

        Long noOfElements = placeDetailService.countPlaceDetailsByLocationTypeAndRatingRange(locationType, ratingFrom, ratingEnd);

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
            jsonObject.addProperty("noOfElements", noOfElements);
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


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-reviews-of-location/{locationId}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void getReviewsOfLocationByLocationIdPageAndSize(@PathVariable Integer locationId, @PathVariable Integer page, @PathVariable Integer size, HttpServletRequest request, HttpServletResponse response) {
        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO
        Location location = locationService.getLocationById(locationId);
        if (location != null) {
            Place place = placeService.getPlaceByLocation(location);
            List<Review> reviews = reviewService.getAllReviewsByPlacePageAndSizeOrderByDate(place, page, size);
            Long noOfElements = reviewService.countReviewsByPlace(place);
            Long oneStarRated = reviewService.countReviewsByPlaceAndRating(place, 1);
            Long twoStarRated = reviewService.countReviewsByPlaceAndRating(place, 2);
            Long threeStarRated = reviewService.countReviewsByPlaceAndRating(place, 3);
            Long fourStarRated = reviewService.countReviewsByPlaceAndRating(place, 4);
            Long fiveStarRated = reviewService.countReviewsByPlaceAndRating(place, 5);
            try {
                PrintWriter writer = response.getWriter();
                Gson gson = new Gson();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("content", gson.toJson(reviews));
                jsonObject.addProperty("locationId", locationId);
                jsonObject.addProperty("page", page);
                jsonObject.addProperty("size", size);
                jsonObject.addProperty("noOfElements", noOfElements);
                jsonObject.addProperty("oneStarRated", oneStarRated);
                jsonObject.addProperty("twoStarRated", twoStarRated);
                jsonObject.addProperty("threeStarRated", threeStarRated);
                jsonObject.addProperty("fourStarRated", fourStarRated);
                jsonObject.addProperty("fiveStarRated", fiveStarRated);
                Long endTime = System.currentTimeMillis();
                jsonObject.addProperty("timeTaken", (endTime - startTime) / 1000);

                writer.print(jsonObject);
                writer.flush();
                response.setStatus(200);
            } catch (IOException e) {
                response.setStatus(500);
                e.printStackTrace();
            }
        } else {
            response.setStatus(404);
        }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-dashboard", method = RequestMethod.GET, produces = "application/json")
    public void getdashboard(@RequestParam(required = false) String wardName, HttpServletRequest request, HttpServletResponse response) {
        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO
        //TODO add wardName
        Long totalToilets = placeDetailService.countPlaceDetails();
        Long fiveStarsRated = placeDetailService.countPlaceDetailsByRatingRange(5.0, 5.0);
        Long threeOrLessStarsRated = placeDetailService.countPlaceDetailsByRatingRange(0.0, 3.0);

        List<String> wardsList = new ArrayList<>();
        List<String> staffsList = new ArrayList<>();
        List<String> locationTypes = new ArrayList<>();
        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("totalToilets", totalToilets);
            jsonObject.addProperty("fiveStarsRated", fiveStarsRated);
            jsonObject.addProperty("threeOrLessStarsRated", threeOrLessStarsRated);
            jsonObject.addProperty("wardsList", gson.toJson(wardsList));
            jsonObject.addProperty("staffsList", gson.toJson(staffsList));
            jsonObject.addProperty("locationTypes", gson.toJson(locationTypes));
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