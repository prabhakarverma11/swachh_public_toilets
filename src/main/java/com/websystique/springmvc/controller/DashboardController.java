package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class DashboardController {

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceDetailService placeDetailService;

    @Autowired
    LocationService locationService;

    @Autowired
    ReportService reportService;

    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashBoard(ModelMap model) {

        List<Location> locations = locationService.getAllLocationsByPageAndSize(0, 100);
        List<Report> reports = reportService.getReportsListByLocationsBetweenDates(locations, "", "");
        model.addAttribute("reportsList", reports);
        return "dashboard";
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */

    @RequestMapping(value = "/listing-locations", method = RequestMethod.GET)
    public String listingLocations(ModelMap model) {

        List<Location> allLocations = locationService.getAllLocationsByPageAndSize(0, 20);
        model.addAttribute("locationsList", allLocations);

        return "locationslist";
    }

    @RequestMapping(value = "/location-detail-{locationId}", method = RequestMethod.GET)
    public String listingLocations(@PathVariable Integer locationId, HttpServletRequest request, ModelMap model) {

        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String dateRange = request.getParameter("date_range");

        Location location = locationService.getLocationById(locationId);
        Place place = placeService.getPlaceByLocation(location);

        Double overallRating = reviewService.getOverallRatingByPlace(place);
        Double averageRating = null;

        try {
            averageRating = reviewService.getAverageRatingByPlaceBetweenDates(place, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long reviewsCount = null;
        try {
            reviewsCount = reviewService.countReviewsByPlaceBetweenDates(place, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long totalReviews = reviewService.countReviewsByPlace(place);

        List<Review> reviewsList = null;
        try {
            reviewsList = reviewService.getReviewsByPlaceBetweenDates(place, startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("location", location);
        model.addAttribute("overallRating", overallRating);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewsCount", reviewsCount);
        model.addAttribute("totalReviews", totalReviews);
        model.addAttribute("reviews", reviewsList);

        if (dateRange == null || dateRange.equals(""))
            dateRange = "today";

        model.addAttribute("dateRange", dateRange);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "locationdetail";
    }

    @ResponseBody
    @RequestMapping(value = "/fetch-rating-and-reviews/{locationId}", method = RequestMethod.GET, produces = "application/json")
    public void fetchRatingAndReviewsById(@PathVariable Integer locationId, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        Location location = locationService.getLocationById(locationId);
        Place place = placeService.getPlaceByLocation(location);
        PlaceDetail placeDetail = placeDetailService.getPlaceDetailByPlace(place);
        List<Review> reviews = reviewService.getReviewsByPlaceBetweenDates(place, null, null);
        Double rating = reviewService.getOverallRatingByPlace(place);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rating", rating);
        jsonObject.addProperty("reviews", gson.toJson(reviews));

        writer.write(jsonObject.toString());

    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(ModelMap model) {

        List<Location> allLocations = locationService.getAllLocationsByPageAndSize(0, 20);
        model.addAttribute("locationsList", allLocations);

        return "home";
    }

    @RequestMapping(value = "/admin-dashboard", method = RequestMethod.GET)
    public String getAdminDashboard(ModelMap modelMap) {

        return "admindashboard";
    }

}