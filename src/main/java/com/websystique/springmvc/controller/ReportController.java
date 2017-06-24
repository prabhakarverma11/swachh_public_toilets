package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.LocationService;
import com.websystique.springmvc.service.PlaceDetailService;
import com.websystique.springmvc.service.PlaceService;
import com.websystique.springmvc.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
//@SessionAttributes("roles")
public class ReportController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    LocationService locationService;

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceDetailService placeDetailService;

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String showReport(HttpServletRequest request, ModelMap model) {

        //TODO remove these lines
        List<Location> locations = locationService.getAllLocations();


        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        Integer locationId = 1;
        List<Report> reportsList = new ArrayList<Report>();

//        Location location = locationService.getLocationById(locationId);
        for (Location location: locations) {
            Place place = placeService.getPlaceByLocation(location);
            PlaceDetail placeDetail = placeDetailService.getPlaceDetailByPlace(place);
            List<Review> reviews = null;
            try {
                reviews = reviewService.getReviewsByPlaceBetweenDates(place, startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Report report = new Report();
            report.setLocation(location);
            report.setPlace(place);
            report.setPlaceDetail(placeDetail);
            Gson gson = new Gson();
            report.setReviews(gson.toJson(reviews));

            reportsList.add(report);
        }
        model.addAttribute("reportsList", reportsList);
//        model.addAttribute("reviews", gson.toJson(reviews));
        return "report";
    }

}