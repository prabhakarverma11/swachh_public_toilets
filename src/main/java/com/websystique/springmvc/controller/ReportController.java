package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Report;
import com.websystique.springmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    ReportService reportService;

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String showReport(HttpServletRequest request, ModelMap model) {

        //TODO remove these lines
        List<Location> locations = locationService.getAllLocationsByPageAndSize(0, 20);


        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        String dateRange = request.getParameter("date_range");


        Integer locationId = 1;
        List<Report> reportsList = reportService.getReportsListByLocationsBetweenDates(locations, startDate, endDate);
        model.addAttribute("reportsList", reportsList);

        if (dateRange == null || dateRange.equals(""))
            dateRange = "today";

        model.addAttribute("dateRange", dateRange);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "report";
    }

}