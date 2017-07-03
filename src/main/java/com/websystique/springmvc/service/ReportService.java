package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.model.Report;

import java.util.List;

public interface ReportService {

    List<Report> getReportsListByLocationsBetweenDates(List<Location> locations, String startDate, String endDate);

    List<Report> getReportsListByPlaceDetailsBetweenDates(List<PlaceDetail> placeDetails, String startDate, String endDate);

}