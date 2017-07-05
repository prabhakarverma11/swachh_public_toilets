package com.websystique.springmvc.controller;

import au.com.bytecode.opencsv.CSVWriter;
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
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * Response is having report of locations with given criteria
     *
     * @param locationType - this is type of toilet location e.g. METRO, HOSPITAL
     * @param startDate    - this is start date for rating and number of reviews
     * @param endDate      -this is end date for rating and number of reviews
     * @param ulbName      - this is to filter the location data by the name of ULB
     * @param ratingFrom   -this is to filter the location having rating greater than this
     * @param ratingEnd    -this is to filter the location having rating less than this
     * @param page         -this is for the pagination, and this is required, by default send 1
     * @param size         -this is for the pagination and this is also required, has no default value, send -INT_MAX if want to have all locations
     * @param request      -this is request having all the parameters as well as the URL
     * @param response     -the response is of application/json type
     *                     {host}:{port}/api/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}?locationType={locationType}&ulbName={ulbName}&startDate={startDate}&endDate={endDate}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void getReportByLocationTypeRatingRangeAndBetweenDatesByPageAndSize(
            @RequestParam(required = false) String locationType,
            @RequestParam(required = false) String ulbName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PathVariable Double ratingFrom,
            @PathVariable Double ratingEnd,
            @PathVariable Integer page,
            @PathVariable Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
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

        //validation of request params TODO

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


    /**
     * Response is having reviews of a location with given criteria
     *
     * @param locationId- this is the id of location we are requesting reviews for,
     * @param page-       this is required parameter, send 1 by default, page number
     * @param size-       this is also required parameter, size of the requested reviews list
     * @param request-    request
     * @param response-   response of application/json type
     *                    {host}:{port}/api/get-reviews-of-location/{locationId}/{page}/{size}?ulbName={ulbName}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-reviews-of-location/{locationId}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void getReviewsOfLocationByPageAndSize(
            @PathVariable Integer locationId,
            @PathVariable Integer page,
            @PathVariable Integer size,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
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

    /**
     * Response is having reviews of a location with given criteria
     *
     * @param locationId- this is the id of location we are requesting reviews for,
     * @param request-    request
     * @param response-   response of application/json type
     *                    {host}:{port}/api/get-rating-counts-of-location/{locationId}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-rating-counts-of-location/{locationId}", method = RequestMethod.GET, produces = "application/json")
    public void getRatingCountsOfLocationByPageAndSize(
            @PathVariable Integer locationId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info(request.getServletPath() +
                ", locationId: " + locationId
        );

        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Location location = locationService.getLocationById(locationId);
        if (location != null) {
            Place place = placeService.getPlaceByLocation(location);
            try {
                PrintWriter writer = response.getWriter();
                Gson gson = new Gson();

                JsonObject jsonObject = new JsonObject();

                JsonObject content = new JsonObject();
                for (Long i = 0L; i < 7L; i++) {
                    Long millis = System.currentTimeMillis() - i * 24 * 60 * 60 * 1000;
                    Date dateToFilter = new Date(millis);

                    JsonObject dayWiseData = new JsonObject();
                    dayWiseData.addProperty("1", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 1, 1, dateToFilter, dateToFilter));
                    dayWiseData.addProperty("2", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 2, 1, dateToFilter, dateToFilter));
                    dayWiseData.addProperty("3", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 3, 1, dateToFilter, dateToFilter));
                    dayWiseData.addProperty("4", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 4, 1, dateToFilter, dateToFilter));
                    dayWiseData.addProperty("5", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 5, 1, dateToFilter, dateToFilter));

                    content.addProperty(new SimpleDateFormat("dd-MM-yyyy").format(dateToFilter), gson.toJson(dayWiseData));
                }

                jsonObject.addProperty("content", gson.toJson(content));

                jsonObject.addProperty("locationId", locationId);
                Long endTime = System.currentTimeMillis();
                jsonObject.addProperty("timeTaken", (endTime - startTime) / 1000);

                writer.print(jsonObject);
                writer.flush();
                response.setStatus(200);

                logger.info(request.getServletPath() +
                        ", content: " + gson.toJson(content) +
                        ", timeTaken: " + (endTime - startTime) / 1000
                );
            } catch (IOException e) {
                response.setStatus(500);
                e.printStackTrace();

                Long endTime = System.currentTimeMillis();
                logger.info(request.getServletPath() +
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

    /**
     * response having all other data for public dashboard filtered by ULB
     *
     * @param ulbName-  ULB name for filtering
     * @param request-  request
     * @param response- response of application/json type
     *                  {host}:{port}/api/get-dashboard?ulbName={ulbName}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get-dashboard", method = RequestMethod.GET, produces = "application/json")
    public void getDashboard(
            @RequestParam(required = false) String ulbName,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info(request.getServletPath() +
                ", ulbName: " + ulbName
        );

        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO

        List<Integer> locationIds = placeULBMapService.getLocationIdsByULBNameAndLocationType(ulbName, null);
        Long totalToilets = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, 0.0, 5.0);
        Long fiveStarsRated = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, 5.0, 5.0);
        Long threeOrLessStarsRated = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, 0.0, 3.0);

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


    /**
     * response having all other data for admin dashboard filtered by ULB
     *
     * @param ulbName-  ULB name for filtering
     * @param request-  request
     * @param response- response of application/json type
     *                  {host}:{port}/api/admin/get-dashboard?ulbName={ulbName}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/admin/get-dashboard", method = RequestMethod.GET, produces = "application/json")
    public void getAdminDashboard(
            @RequestParam(required = false) String ulbName,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info(request.getServletPath() +
                ", ulbName: " + ulbName
        );

        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validataion TODO

        List<Integer> locationIds = placeULBMapService.getLocationIdsByULBNameAndLocationType(ulbName, null);
        Long totalToilets = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, 0.0, 5.0);
        Long fiveStarsRated = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, 5.0, 5.0);
        Long threeOrLessStarsRated = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, 0.0, 3.0);

        List<String> ulbsList = placeULBMapService.getULBList();
        List<String> staffsList = new ArrayList<>();
        List<String> locationTypes = locationService.getLocationTypes();

        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();

            Long millisInOneDay = 24 * 60 * 60 * 1000L;

            JsonObject ratingDistribution = new JsonObject();
            //five star rated toilets
            JsonObject fiveStar = new JsonObject();
            fiveStar.addProperty("tillDate", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(0L), new Date(), 5));
            fiveStar.addProperty("yesterday", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - millisInOneDay), new Date(System.currentTimeMillis() - millisInOneDay), 5));
            fiveStar.addProperty("lastWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 7 * millisInOneDay), new Date(), 5));
            fiveStar.addProperty("lastTwoWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 14 * millisInOneDay), new Date(), 5));
            fiveStar.addProperty("lastOneMonth", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 30 * millisInOneDay), new Date(), 5));
            ratingDistribution.addProperty("fiveStar", gson.toJson(fiveStar));

            //four star rated toilets
            JsonObject fourStar = new JsonObject();
            fourStar.addProperty("tillDate", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(0L), new Date(), 4));
            fourStar.addProperty("yesterday", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - millisInOneDay), new Date(System.currentTimeMillis() - millisInOneDay), 4));
            fourStar.addProperty("lastWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 7 * millisInOneDay), new Date(), 4));
            fourStar.addProperty("lastTwoWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 14 * millisInOneDay), new Date(), 4));
            fourStar.addProperty("lastOneMonth", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 30 * millisInOneDay), new Date(), 4));
            ratingDistribution.addProperty("fourStar", gson.toJson(fourStar));

            //three star rated toilets
            JsonObject threeStar = new JsonObject();
            threeStar.addProperty("tillDate", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(0L), new Date(), 3));
            threeStar.addProperty("yesterday", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - millisInOneDay), new Date(System.currentTimeMillis() - millisInOneDay), 3));
            threeStar.addProperty("lastWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 7 * millisInOneDay), new Date(), 3));
            threeStar.addProperty("lastTwoWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 14 * millisInOneDay), new Date(), 3));
            threeStar.addProperty("lastOneMonth", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 30 * millisInOneDay), new Date(), 3));
            ratingDistribution.addProperty("threeStar", gson.toJson(threeStar));

            //two star rated toilets
            JsonObject twoStar = new JsonObject();
            twoStar.addProperty("tillDate", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(0L), new Date(), 2));
            twoStar.addProperty("yesterday", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - millisInOneDay), new Date(System.currentTimeMillis() - millisInOneDay), 2));
            twoStar.addProperty("lastWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 7 * millisInOneDay), new Date(), 2));
            twoStar.addProperty("lastTwoWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 14 * millisInOneDay), new Date(), 2));
            twoStar.addProperty("lastOneMonth", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 30 * millisInOneDay), new Date(), 2));
            ratingDistribution.addProperty("twoStar", gson.toJson(twoStar));

            //one star rated toilets
            JsonObject oneStar = new JsonObject();
            oneStar.addProperty("tillDate", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(0L), new Date(), 1));
            oneStar.addProperty("yesterday", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - millisInOneDay), new Date(System.currentTimeMillis() - millisInOneDay), 1));
            oneStar.addProperty("lastWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 7 * millisInOneDay), new Date(), 1));
            oneStar.addProperty("lastTwoWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 14 * millisInOneDay), new Date(), 1));
            oneStar.addProperty("lastOneMonth", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 30 * millisInOneDay), new Date(), 1));
            ratingDistribution.addProperty("oneStar", gson.toJson(oneStar));

            //noFeedBack rated toilets
            JsonObject noFeedBack = new JsonObject();
            noFeedBack.addProperty("tillDate", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(0L), new Date(), 0));
            noFeedBack.addProperty("yesterday", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - millisInOneDay), new Date(System.currentTimeMillis() - millisInOneDay), 0));
            noFeedBack.addProperty("lastWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 7 * millisInOneDay), new Date(), 0));
            noFeedBack.addProperty("lastTwoWeek", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 14 * millisInOneDay), new Date(), 0));
            noFeedBack.addProperty("lastOneMonth", reviewService.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, new Date(System.currentTimeMillis() - 30 * millisInOneDay), new Date(), 0));
            ratingDistribution.addProperty("noFeedBack", gson.toJson(noFeedBack));

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("totalToilets", totalToilets);
            jsonObject.addProperty("fiveStarsRated", fiveStarsRated);
            jsonObject.addProperty("threeOrLessStarsRated", threeOrLessStarsRated);
            jsonObject.addProperty("ulbsList", gson.toJson(ulbsList));
            jsonObject.addProperty("staffsList", gson.toJson(staffsList));
            jsonObject.addProperty("locationTypes", gson.toJson(locationTypes));
            jsonObject.addProperty("ratingDistribution", gson.toJson(ratingDistribution));
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
                    ", ratingDistribution: " + ratingDistribution +
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

    /**
     * Downloads report of locations with given criteria
     *
     * @param locationType - this is type of toilet location e.g. METRO, HOSPITAL
     * @param startDate    - this is start date for rating and number of reviews
     * @param endDate      -this is end date for rating and number of reviews
     * @param ulbName      - this is to filter the location data by the name of ULB
     * @param ratingFrom   -this is to filter the location having rating greater than this
     * @param ratingEnd    -this is to filter the location having rating less than this
     * @param page         -this is for the pagination, and this is required, by default send 1
     * @param size         -this is for the pagination and this is also required, has no default value, send -INT_MAX if want to have all locations
     * @param request      -this is request having all the parameters as well as the URL
     * @param response     -the response is of application/json type
     *                     {host}:{port}/api/download-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}?locationType={locationType}&ulbName={ulbName}&startDate={startDate}&endDate={endDate}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/download-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void downloadReportByLocationTypeRatingRangeAndBetweenDatesByPageAndSize(
            @RequestParam(required = false) String locationType,
            @RequestParam(required = false) String ulbName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PathVariable Double ratingFrom,
            @PathVariable Double ratingEnd,
            @PathVariable Integer page,
            @PathVariable Integer size,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
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

        try {
            String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String fileName = "RatingAndReviewsReport_" + today + ".csv";

            response.setContentType("application/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setCharacterEncoding("utf-8");


            File csvFile = getReportCSVFile(fileName,
                    locationType, ulbName,
                    startDate, endDate,
                    ratingFrom, ratingEnd,
                    page, size,
                    request, response,
                    session);

            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(csvFile);
            int readBytes = in.available();
            byte[] content = new byte[readBytes];
            in.read(content);
            out.write(content);

            in.close();
            out.close();
            Long endTime = System.currentTimeMillis();

            logger.info(request.getServletPath() +
                    ", timeTaken: " + (endTime - startTime) / 1000
            );
        } catch (IOException e) {
            e.printStackTrace();
            Long endTime = System.currentTimeMillis();
            logger.info(request.getServletPath() +
                    ", timeTaken: " + (endTime - startTime) / 1000 +
                    ", error: " + e.getMessage()
            );
        }
    }

    private File getReportCSVFile(String fileName,
                                  String locationType, String ulbName,
                                  String startDate, String endDate,
                                  Double ratingFrom, Double ratingEnd,
                                  Integer page, Integer size,
                                  HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException {


        List<Integer> locationIds = placeULBMapService.getLocationIdsByULBNameAndLocationType(ulbName, locationType);
        List<PlaceDetail> placeDetails = placeDetailService.getAllPlaceDetailsByLocationIdsRatingRangePageAndSize(locationIds, ratingFrom, ratingEnd, page, size);

        List<Report> reports = reportService.getReportsListByPlaceDetailsBetweenDates(placeDetails, startDate, endDate);
        Long noOfElements = placeDetailService.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, ratingFrom, ratingEnd);

        File csvFile = new File(fileName);
        csvFile.setReadable(true, false);
        csvFile.setWritable(true, false);
        csvFile.createNewFile();

        CSVWriter writer = new CSVWriter(new FileWriter(csvFile.getAbsolutePath()));

        writer.writeNext(new String[]{"S. No.", "Name", "Address", "Type", "Avg. Rating", "Reviews"});

        String line = "";
        int count = 1;
        for (Report report : reports) {
            line = "";
            line += (count++) + "|";
            line += report.getLocation().getName() + "|";
            line += report.getLocation().getAddress() + "|";
            line += report.getLocation().getType() + "|";
            line += report.getAverageRating() + "|";
            line += report.getReviewsCount() + "|";

            writer.writeNext(line.split("\\|"));
        }
        writer.close();
        return csvFile;
    }

}