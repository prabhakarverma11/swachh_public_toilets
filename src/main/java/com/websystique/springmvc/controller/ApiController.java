package com.websystique.springmvc.controller;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.*;
import com.websystique.springmvc.utils.UtilMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
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

    @Autowired
    AdminVerificationService adminVerificationService;

    UtilMethods utilMethods = new UtilMethods();

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

        Long noOfElements = null;


        List<Report> reports = null;
        try {
            noOfElements = reportService.countReportsListBetweenDatesByLocationIdsAndRatingRange(locationIds, startDate, endDate, ratingFrom, ratingEnd);
            reports = reportService.getReportsListBetweenDatesByLocationIdsRatingRangePageAndSize(locationIds, startDate, endDate, ratingFrom, ratingEnd, page, size);
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
                    ", reports.size: " + reports.size() +
                    ", noOfElements: " + noOfElements +
                    ", timeTaken: " + (endTime - startTime) / 1000
            );
        } catch (IOException e) {
            response.setStatus(500);
            e.printStackTrace();

            Long endTime = System.currentTimeMillis();
            logger.info(request.getServletPath() +
                    ", reports.size: " + reports.size() +
                    ", noOfElements: " + noOfElements +
                    ", timeTaken: " + (endTime - startTime) / 1000 +
                    ", error: " + e.getMessage()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Response is having report of locations with given criteria
     *
     * @param request  -this is request having all the parameters as well as the URL
     * @param response -the response is of application/json type
     *                 {host}:{port}/api/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}?locationType={locationType}&ulbName={ulbName}&startDate={startDate}&endDate={endDate}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/save-location-type", method = RequestMethod.POST, produces = "application/json")
    public void saveLocationType(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info(request.getServletPath());

        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validation of request params TODO
        Integer locationId = Integer.parseInt(request.getParameter("locationId"));
        String locationType = request.getParameter("locationType");
        String locationTypeArr[] = locationType.split(":");

        String category = request.getParameter("category");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        AdminVerification adminVerification = new AdminVerification();
        adminVerification.setLocation(locationService.getLocationById(locationId));
        adminVerification.setName(name);
        adminVerification.setCategory(category);
        adminVerification.setEmail(email);
        adminVerification.setLocationType(locationTypeArr.length >= 2 ? locationTypeArr[1] : "");
        adminVerification.setPhone(phone);
        adminVerificationService.save(adminVerification);

        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();

            Long endTime = System.currentTimeMillis();
            jsonObject.addProperty("timeTaken", (endTime - startTime) / 1000);

            writer.print(jsonObject);
            writer.flush();
            response.setStatus(200);

            logger.info(request.getServletPath() +
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
    }

    /**
     * Response is having report of locations with given criteria
     *
     * @param request  -this is request having all the parameters as well as the URL
     * @param response -the response is of application/json type
     *                 {host}:{port}/api/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}?locationType={locationType}&ulbName={ulbName}&startDate={startDate}&endDate={endDate}
     */
    @CrossOrigin
    @RequestMapping(value = "/admin/accept", method = RequestMethod.POST)
    public String accept(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        AdminVerification adminVerification = adminVerificationService.getAdminVerificationById(id);

        if (adminVerification.getLocationType() != null && !adminVerification.getLocationType().equals("")) {
            Location location = adminVerification.getLocation();
            location.setType(adminVerification.getLocationType());
            locationService.update(location);
            adminVerificationService.delete(adminVerification);
        } else if (adminVerification.getULBName() != null && !adminVerification.getULBName().equals("")) {
            PlaceULBMap placeULBMap = placeULBMapService.getPlaceULBMapByPlace(placeService.getPlaceByLocation(adminVerification.getLocation()));
            placeULBMap.setULBName(adminVerification.getULBName());
            placeULBMapService.update(placeULBMap);
            adminVerificationService.delete(adminVerification);
        }
        return "redirect:/admin/review-dashboard";
    }


    /**
     * Response is having report of locations with given criteria
     *
     * @param request  -this is request having all the parameters as well as the URL
     * @param response -the response is of application/json type
     *                 {host}:{port}/api/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}?locationType={locationType}&ulbName={ulbName}&startDate={startDate}&endDate={endDate}
     */
    @CrossOrigin
    @RequestMapping(value = "/admin/reject", method = RequestMethod.POST)
    public String reject(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        AdminVerification adminVerification = adminVerificationService.getAdminVerificationById(id);
        adminVerificationService.delete(adminVerification);
        return "redirect:/admin/review-dashboard";
    }

    /**
     * Response is having report of locations with given criteria
     *
     * @param request  -this is request having all the parameters as well as the URL
     * @param response -the response is of application/json type
     *                 {host}:{port}/api/get-report-of-locations/{ratingFrom}/{ratingEnd}/{page}/{size}?locationType={locationType}&ulbName={ulbName}&startDate={startDate}&endDate={endDate}
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/save-ulb-name", method = RequestMethod.POST, produces = "application/json")
    public void saveULBName(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info(request.getServletPath());

        Long startTime = System.currentTimeMillis();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //validation of request params TODO
        Integer locationId = Integer.parseInt(request.getParameter("locationId"));
        String ulbName = request.getParameter("ulbName");
        String ulbNameArr[] = ulbName.split(":");

        String category = request.getParameter("category");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        AdminVerification adminVerification = new AdminVerification();
        adminVerification.setLocation(locationService.getLocationById(locationId));
        adminVerification.setName(name);
        adminVerification.setCategory(category);
        adminVerification.setEmail(email);
        adminVerification.setULBName(ulbNameArr.length >= 2 ? ulbNameArr[1] : "");
        adminVerification.setPhone(phone);
        adminVerificationService.save(adminVerification);

        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();

            Long endTime = System.currentTimeMillis();
            jsonObject.addProperty("timeTaken", (endTime - startTime) / 1000);

            writer.print(jsonObject);
            writer.flush();
            response.setStatus(200);

            logger.info(request.getServletPath() +
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
                    dayWiseData.addProperty("1", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 1, 1, utilMethods.formatStartDate(dateToFilter), utilMethods.formatEndDate(dateToFilter)));
                    dayWiseData.addProperty("2", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 2, 2, utilMethods.formatStartDate(dateToFilter), utilMethods.formatEndDate(dateToFilter)));
                    dayWiseData.addProperty("3", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 3, 3, utilMethods.formatStartDate(dateToFilter), utilMethods.formatEndDate(dateToFilter)));
                    dayWiseData.addProperty("4", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 4, 4, utilMethods.formatStartDate(dateToFilter), utilMethods.formatEndDate(dateToFilter)));
                    dayWiseData.addProperty("5", reviewService.countReviewsByPlaceAndRatingRangeBetweenDates(place, 5, 5, utilMethods.formatStartDate(dateToFilter), utilMethods.formatEndDate(dateToFilter)));

                    content.addProperty(new SimpleDateFormat("dd-MM-yyyy").format(dateToFilter), gson.toJson(dayWiseData));
                }

                jsonObject.addProperty("content", gson.toJson(content));
                JsonObject overall = new JsonObject();
                overall.addProperty("0", reviewService.countReviewsByPlaceAndRating(place, 0));
                overall.addProperty("1", reviewService.countReviewsByPlaceAndRating(place, 1));
                overall.addProperty("2", reviewService.countReviewsByPlaceAndRating(place, 2));
                overall.addProperty("3", reviewService.countReviewsByPlaceAndRating(place, 3));
                overall.addProperty("4", reviewService.countReviewsByPlaceAndRating(place, 4));
                overall.addProperty("5", reviewService.countReviewsByPlaceAndRating(place, 5));
                jsonObject.addProperty("overall", gson.toJson(overall));


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

        //validation TODO

        List<Integer> locationIds = placeULBMapService.getLocationIdsByULBNameAndLocationType(ulbName, null);
        Long millisInOneDay = 60 * 60 * 24 * 1000L;
        Date yesterday = new Date((System.currentTimeMillis() / millisInOneDay) * millisInOneDay - millisInOneDay);

        List<Object[]> toiletsReviewedTillDate = reviewService.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, new Date(0L), new Date());
//        List<Object[]> toiletsReviewedYesterday = reviewService.getLocationIdsByLocationIdsAndBetweenDates(locationIds, new Date(System.currentTimeMillis() - millisInOneDay), new Date(System.currentTimeMillis() - millisInOneDay));

        Long totalToiletsReviewed = (long) toiletsReviewedTillDate.size();
        Long fourToFiveStarsRated = countFiveStars(toiletsReviewedTillDate) + countFourStars(toiletsReviewedTillDate);
        Long threeOrLessStarsRated = countThreeStars(toiletsReviewedTillDate) + countTwoStars(toiletsReviewedTillDate) + countOneStar(toiletsReviewedTillDate);

//        Long totalToiletsYesterday = (long) toiletsReviewedYesterday.size();
//        Long fourToFiveStarsRatedYesterday = countFiveStars(toiletsReviewedYesterday) + countFourStars(toiletsReviewedYesterday);
//        Long threeOrLessStarsRatedYesterday = countThreeStars(toiletsReviewedYesterday) + countTwoStars(toiletsReviewedTillDate) + countOneStar(toiletsReviewedYesterday);

        List<String> ulbsList = placeULBMapService.getULBList();
        List<String> staffsList = new ArrayList<>();
        List<String> locationTypes = locationService.getLocationTypes();
        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("totalToilets", locationIds.size());
            jsonObject.addProperty("totalReviews", reviewService.countReviewsByLocationIds(locationIds));
            jsonObject.addProperty("totalComments", reviewService.countCommentsByLocationIds(locationIds));

            jsonObject.addProperty("totalToiletsReviewed", totalToiletsReviewed);
            jsonObject.addProperty("fourToFiveStarsRated", fourToFiveStarsRated);
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
                    ", totalToilets: " + totalToiletsReviewed +
                    ", fourToFiveStarsRated: " + fourToFiveStarsRated +
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
                    ", totalToilets: " + totalToiletsReviewed +
                    ", fourToFiveStarsRated: " + fourToFiveStarsRated +
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

        //validation TODO

        List<Integer> locationIds = placeULBMapService.getLocationIdsByULBNameAndLocationType(ulbName, null);

        Long millisInOneDay = 60 * 60 * 24 * 1000L;
        Date yesterday = new Date((System.currentTimeMillis() / millisInOneDay) * millisInOneDay - millisInOneDay);

        List<Object[]> toiletsReviewedTillDate = reviewService.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, new Date(0L), new Date());
        List<Object[]> toiletsReviewedYesterday = reviewService.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, new Date(millisInOneDay * ((System.currentTimeMillis() - millisInOneDay) / millisInOneDay)), new Date(millisInOneDay * ((System.currentTimeMillis()) / millisInOneDay)));
        List<Object[]> toiletsReviewedLastWeek = reviewService.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, new Date(System.currentTimeMillis() - 7 * millisInOneDay), new Date());
        List<Object[]> toiletsReviewedLastTwoWeek = reviewService.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, new Date(System.currentTimeMillis() - 14 * millisInOneDay), new Date());
        List<Object[]> toiletsReviewedLastMonth = reviewService.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, new Date(millisInOneDay * ((System.currentTimeMillis() - 30 * millisInOneDay) / millisInOneDay)), new Date(millisInOneDay * (System.currentTimeMillis() / millisInOneDay)));

        Long totalToiletsReviewed = (long) toiletsReviewedTillDate.size();
        Long fourToFiveStarsRated = countFiveStars(toiletsReviewedTillDate) + countFourStars(toiletsReviewedTillDate);
        Long threeOrLessStarsRated = countThreeStars(toiletsReviewedTillDate) + countTwoStars(toiletsReviewedTillDate) + countOneStar(toiletsReviewedTillDate);


        List<String> ulbsList = placeULBMapService.getULBList();
        List<String> staffsList = new ArrayList<>();
        List<String> locationTypes = locationService.getLocationTypes();

        try {
            PrintWriter writer = response.getWriter();
            Gson gson = new Gson();


            JsonObject ratingDistribution = new JsonObject();
            //five star rated toilets
            JsonObject fiveStar = new JsonObject();

            fiveStar.addProperty("tillDate", countFiveStars(toiletsReviewedTillDate));
            fiveStar.addProperty("yesterday", countFiveStars(toiletsReviewedYesterday));
            fiveStar.addProperty("lastWeek", countFiveStars(toiletsReviewedLastWeek));
            fiveStar.addProperty("lastTwoWeek", countFiveStars(toiletsReviewedLastTwoWeek));
            fiveStar.addProperty("lastOneMonth", countFiveStars(toiletsReviewedLastMonth));
            ratingDistribution.addProperty("fiveStar", gson.toJson(fiveStar));

            //four star rated toilets
            JsonObject fourStar = new JsonObject();
            fourStar.addProperty("tillDate", countFourStars(toiletsReviewedTillDate));
            fourStar.addProperty("yesterday", countFourStars(toiletsReviewedYesterday));
            fourStar.addProperty("lastWeek", countFourStars(toiletsReviewedLastWeek));
            fourStar.addProperty("lastTwoWeek", countFourStars(toiletsReviewedLastTwoWeek));
            fourStar.addProperty("lastOneMonth", countFourStars(toiletsReviewedLastMonth));
            ratingDistribution.addProperty("fourStar", gson.toJson(fourStar));

            //three star rated toilets
            JsonObject threeStar = new JsonObject();
            threeStar.addProperty("tillDate", countThreeStars(toiletsReviewedTillDate));
            threeStar.addProperty("yesterday", countThreeStars(toiletsReviewedYesterday));
            threeStar.addProperty("lastWeek", countThreeStars(toiletsReviewedLastWeek));
            threeStar.addProperty("lastTwoWeek", countThreeStars(toiletsReviewedLastTwoWeek));
            threeStar.addProperty("lastOneMonth", countThreeStars(toiletsReviewedLastMonth));
            ratingDistribution.addProperty("threeStar", gson.toJson(threeStar));

            //two star rated toilets
            JsonObject twoStar = new JsonObject();
            twoStar.addProperty("tillDate", countTwoStars(toiletsReviewedTillDate));
            twoStar.addProperty("yesterday", countTwoStars(toiletsReviewedYesterday));
            twoStar.addProperty("lastWeek", countTwoStars(toiletsReviewedLastWeek));
            twoStar.addProperty("lastTwoWeek", countTwoStars(toiletsReviewedLastTwoWeek));
            twoStar.addProperty("lastOneMonth", countTwoStars(toiletsReviewedLastMonth));
            ratingDistribution.addProperty("twoStar", gson.toJson(twoStar));

            //one star rated toilets
            JsonObject oneStar = new JsonObject();
            oneStar.addProperty("tillDate", countOneStar(toiletsReviewedTillDate));
            oneStar.addProperty("yesterday", countOneStar(toiletsReviewedYesterday));
            oneStar.addProperty("lastWeek", countOneStar(toiletsReviewedLastWeek));
            oneStar.addProperty("lastTwoWeek", countOneStar(toiletsReviewedLastTwoWeek));
            oneStar.addProperty("lastOneMonth", countOneStar(toiletsReviewedLastMonth));
            ratingDistribution.addProperty("oneStar", gson.toJson(oneStar));

            //noFeedBack rated toilets
            JsonObject noFeedBack = new JsonObject();
            noFeedBack.addProperty("tillDate", countZeroStar(toiletsReviewedTillDate));
            noFeedBack.addProperty("yesterday", countZeroStar(toiletsReviewedYesterday));
            noFeedBack.addProperty("lastWeek", countZeroStar(toiletsReviewedLastWeek));
            noFeedBack.addProperty("lastTwoWeek", countZeroStar(toiletsReviewedLastTwoWeek));
            noFeedBack.addProperty("lastOneMonth", countZeroStar(toiletsReviewedLastMonth));
            ratingDistribution.addProperty("noFeedBack", gson.toJson(noFeedBack));

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("totalToilets", locationIds.size());
            jsonObject.addProperty("totalReviews", reviewService.countReviewsByLocationIds(locationIds));
            jsonObject.addProperty("totalComments", reviewService.countCommentsByLocationIds(locationIds));

            jsonObject.addProperty("totalToiletsReviewed", totalToiletsReviewed);
            jsonObject.addProperty("fourToFiveStarsRated", fourToFiveStarsRated);
            jsonObject.addProperty("threeOrLessStarsRated", threeOrLessStarsRated);

            Long totalToiletsYesterday = (long) toiletsReviewedYesterday.size();
            Long fourToFiveStarsRatedYesterday = countFiveStars(toiletsReviewedYesterday) + countFourStars(toiletsReviewedYesterday);
            Long threeOrLessStarsRatedYesterday = countThreeStars(toiletsReviewedYesterday) + countTwoStars(toiletsReviewedYesterday) + countOneStar(toiletsReviewedYesterday);

            jsonObject.addProperty("totalToiletsYesterday", totalToiletsYesterday);
            jsonObject.addProperty("fourToFiveStarsRatedYesterday", fourToFiveStarsRatedYesterday);
            jsonObject.addProperty("threeOrLessStarsRatedYesterday", threeOrLessStarsRatedYesterday);

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
                    ", totalToilets: " + totalToiletsReviewed +
                    ", fourToFiveStarsRated: " + fourToFiveStarsRated +
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
                    ", totalToilets: " + totalToiletsReviewed +
                    ", fourToFiveStarsRated: " + fourToFiveStarsRated +
                    ", threeOrLessStarsRated" + threeOrLessStarsRated +
                    ", ulbsList.size: " + ulbsList.size() +
                    ", staffsList.size: " + staffsList.size() +
                    ", locationTypes.size: " + locationTypes.size() +
                    ", timeTaken: " + (endTime - startTime) / 1000 +
                    ", error: " + e.getMessage()
            );

        }
    }

    private Long countFiveStars(List<Object[]> toiletsList) {
        Long count = 0L;
        for (Object[] obj : toiletsList) {
            Double rating = (Double) obj[1];
            if (rating >= 4.5 && rating < 5.5) {
                count++;
            }
        }
        return count;
    }

    private Long countFourStars(List<Object[]> toiletsList) {
        Long count = 0L;
        for (Object[] obj : toiletsList) {
            Double rating = (Double) obj[1];
            if (rating >= 3.5 && rating < 4.5) {
                count++;
            }
        }
        return count;
    }

    private Long countThreeStars(List<Object[]> toiletsList) {
        Long count = 0L;
        for (Object[] obj : toiletsList) {
            Double rating = (Double) obj[1];
            if (rating >= 2.5 && rating < 3.5) {
                count++;
            }
        }
        return count;
    }

    private Long countTwoStars(List<Object[]> toiletsList) {
        Long count = 0L;
        for (Object[] obj : toiletsList) {
            Double rating = (Double) obj[1];
            if (rating >= 1.5 && rating < 2.5) {
                count++;
            }
        }
        return count;
    }

    private Long countOneStar(List<Object[]> toiletsList) {
        Long count = 0L;
        for (Object[] obj : toiletsList) {
            Double rating = (Double) obj[1];
            if (rating < 1.5 && rating > 0.0) {
                count++;
            }
        }
        return count;
    }

    private Long countZeroStar(List<Object[]> toiletsList) {
        Long count = 0L;
        for (Object[] obj : toiletsList) {
            Double rating = (Double) obj[1];
            if (rating == 0.0) {
                count++;
            }
        }
        return count;
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
        } catch (IOException | ParseException e) {
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
                                  HttpSession session) throws IOException, ParseException {


        List<Integer> locationIds = placeULBMapService.getLocationIdsByULBNameAndLocationType(ulbName, locationType);
        List<Report> reports = reportService.getReportsListBetweenDatesByLocationIdsRatingRangePageAndSize(locationIds, startDate, endDate, ratingFrom, ratingEnd, page, size);

        File csvFile = new File(fileName);
        csvFile.setReadable(true, false);
        csvFile.setWritable(true, false);
        csvFile.createNewFile();

        CSVWriter writer = new CSVWriter(new FileWriter(csvFile.getAbsolutePath()));

        writer.writeNext(new String[]{"S. No.", "Address", "Type", "ULB", "Avg. Rating", "No. of Reviews"});

        String line = "";
        int count = 1;
        for (Report report : reports) {
            line = "";
            line += (count++) + "|";
            line += report.getLocation().getAddress() + "|";
            line += report.getLocation().getType() + "|";
            line += placeULBMapService.getPlaceULBMapByPlace(report.getPlace()).getULBName() + "|";
            line += report.getAverageRating() + "|";
            line += report.getReviewsCount() + "|";

            writer.writeNext(line.split("\\|"));
        }
        writer.close();
        return csvFile;
    }


}
