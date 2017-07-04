package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static final Logger logger = LoggerFactory.getLogger(ApiController.class);

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
    public void getReportByLocationTypeRatingRangeAndBetweenDatesByPageAndSize(
            @RequestParam(required = false) String locationType,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String ulbName,
            @PathVariable Double ratingFrom,
            @PathVariable Double ratingEnd,
            @PathVariable Integer page,
            @PathVariable Integer size,
            HttpServletRequest request,
            HttpServletResponse response) {
        logger.info(request.getServletPath() +
                ", locationType: " + locationType +
                ", startDate" + startDate +
                ", endDate: " + endDate +
                ", ulbName: " + ulbName +
                ", ratingFrom: " + ratingFrom +
                ", ratingEnd: " + ratingEnd +
                ", page: " + page +
                ", size: " + size
        );

        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO

        List<Integer> locationIds = placeULBMapService.getLocationIdsByULBNameAndLocationType(ulbName, locationType);
        List<PlaceDetail> placeDetails = placeDetailService.getAllPlaceDetailsByLocationIdsRatingRangePageAndSize(locationIds, ratingFrom, ratingEnd, page, size);

        List<Report> reports = reportService.getReportsListByPlaceDetailsBetweenDates(placeDetails, startDate, endDate);

        Long noOfElements = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, ratingFrom, ratingEnd);

        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", gson.toJson(reports));
            jsonObject.addProperty("locationType", locationType);
            jsonObject.addProperty("startDate", startDate);
            jsonObject.addProperty("endDate", endDate);
            jsonObject.addProperty("ulbName", ulbName);
            jsonObject.addProperty("ratingFrom", ratingFrom);
            jsonObject.addProperty("ratingEnd", ratingEnd);
            jsonObject.addProperty("page", page);
            jsonObject.addProperty("size", size);
            jsonObject.addProperty("noOfElements", noOfElements);
            Long endTime = System.currentTimeMillis();
            jsonObject.addProperty("timeTaken", (endTime - startTime) / 1000);

            writer.print(jsonObject);
            writer.flush();
            response.setStatus(200);

            logger.info(request.getServletPath() +
                    ", placeDetails.size: " + placeDetails.size() +
                    ", reports.size: " + reports.size() +
                    ", noOfElements: " + noOfElements +
                    ", timeTaken: " + (endTime - startTime) / 1000
            );
        } catch (IOException e) {
            response.setStatus(500);
            e.printStackTrace();

            Long endTime = System.currentTimeMillis();
            logger.info(request.getServletPath() +
                    ", placeDetails.size: " + placeDetails.size() +
                    ", reports.size: " + reports.size() +
                    ", noOfElements: " + noOfElements +
                    ", timeTaken: " + (endTime - startTime) / 1000 +
                    ", error: " + e.getMessage()
            );
        }
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-reviews-of-location/{locationId}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void getReviewsOfLocationByLocationIdPageAndSize(@PathVariable Integer locationId, @PathVariable Integer page, @PathVariable Integer size, HttpServletRequest request, HttpServletResponse response) {
        logger.info(request.getServletPath() +
                ", locationId: " + locationId +
                ", page: " + page +
                ", size: " + size
        );

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

                logger.info(request.getServletPath() +
                        ", reviews.size: " + reviews.size() +
                        ", noOfElements: " + noOfElements +
                        ", oneStarRated: " + oneStarRated +
                        ", oneStarRated" + oneStarRated +
                        ", threeStarRated: " + threeStarRated +
                        ", fourStarRated: " + fourStarRated +
                        ", fiveStarRated: " + fiveStarRated +
                        ", timeTaken: " + (endTime - startTime) / 1000
                );
            } catch (IOException e) {
                response.setStatus(500);
                e.printStackTrace();

                Long endTime = System.currentTimeMillis();
                logger.info(request.getServletPath() +
                        ", reviews.size: " + reviews.size() +
                        ", noOfElements: " + noOfElements +
                        ", oneStarRated: " + oneStarRated +
                        ", oneStarRated" + oneStarRated +
                        ", threeStarRated: " + threeStarRated +
                        ", fourStarRated: " + fourStarRated +
                        ", fiveStarRated: " + fiveStarRated +
                        ", timeTaken: " + (endTime - startTime) / 1000 +
                        ", error: " + e.getMessage()
                );
            }
        } else {
            response.setStatus(404);
            logger.info(request.getServletPath() +
                    ", location id not found in database;"
            );
        }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-dashboard", method = RequestMethod.GET, produces = "application/json")
    public void getDashboard(@RequestParam(required = false) String ulbName, HttpServletRequest request, HttpServletResponse response) {
        logger.info(request.getServletPath() +
                ", ulbName: " + ulbName
        );

        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO
        //TODO add ulbName
        Long totalToilets = placeDetailService.countPlaceDetails();
        Long fiveStarsRated = placeDetailService.countPlaceDetailsByRatingRange(5.0, 5.0);
        Long threeOrLessStarsRated = placeDetailService.countPlaceDetailsByRatingRange(0.0, 3.0);

        List<String> ulbsList = placeULBMapService.getULBList();
        List<String> staffsList = new ArrayList<>();
        List<String> locationTypes = locationService.getLocationTypes();
        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("totalToilets", totalToilets);
            jsonObject.addProperty("fiveStarsRated", fiveStarsRated);
            jsonObject.addProperty("threeOrLessStarsRated", threeOrLessStarsRated);
            jsonObject.addProperty("ulbsList", gson.toJson(ulbsList));
            jsonObject.addProperty("staffsList", gson.toJson(staffsList));
            jsonObject.addProperty("locationTypes", gson.toJson(locationTypes));
            Long endTime = System.currentTimeMillis();
            jsonObject.addProperty("timeTaken", (endTime - startTime) / 1000);

            writer.print(jsonObject);
            writer.flush();
            response.setStatus(200);

            logger.info(request.getServletPath() +
                    ", totalToilets: " + totalToilets +
                    ", fiveStarsRated: " + fiveStarsRated +
                    ", threeOrLessStarsRated" + threeOrLessStarsRated +
                    ", ulbsList.size: " + ulbsList.size() +
                    ", staffsList.size: " + staffsList.size() +
                    ", locationTypes.size: " + locationTypes.size() +
                    ", timeTaken: " + (endTime - startTime) / 1000
            );

        } catch (IOException e) {
            response.setStatus(500);
            e.printStackTrace();
            Long endTime = System.currentTimeMillis();
            logger.info(request.getServletPath() +
                    ", totalToilets: " + totalToilets +
                    ", fiveStarsRated: " + fiveStarsRated +
                    ", threeOrLessStarsRated" + threeOrLessStarsRated +
                    ", ulbsList.size: " + ulbsList.size() +
                    ", staffsList.size: " + staffsList.size() +
                    ", locationTypes.size: " + locationTypes.size() +
                    ", timeTaken: " + (endTime - startTime) / 1000 +
                    ", error: " + e.getMessage()
            );

        }
    }


}