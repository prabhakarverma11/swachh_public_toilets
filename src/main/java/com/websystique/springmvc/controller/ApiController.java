package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Report;
import com.websystique.springmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    PlaceService placeService;

    @Autowired
    PinCodeDetailService pinCodeDetailService;

    @ResponseBody
    @RequestMapping(value = "/get-report-of-locations/{startDate}/{endDate}/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public void getReportOfLocationsBetweenDateByPageAndSize(@PathVariable String startDate, @PathVariable String endDate, @PathVariable Integer page, @PathVariable Integer size, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        List<Location> locations = locationService.getAllLocationsByPageAndSize(page, size);
        List<Report> reports = reportService.getReportsListByLocationsBetweenDates(locations, startDate, endDate);

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("content", gson.toJson(reports));
        jsonObject.addProperty("startDate", gson.toJson(startDate));
        jsonObject.addProperty("endDate", gson.toJson(endDate));
        jsonObject.addProperty("page", gson.toJson(page));
        jsonObject.addProperty("size", gson.toJson(size));
        jsonObject.addProperty("noOfElements", gson.toJson(reports.size()));
        jsonObject.addProperty("totalNoOfElements", gson.toJson(locations.size()));
        writer.write(jsonObject.toString());
    }
}