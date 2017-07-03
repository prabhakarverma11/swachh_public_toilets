package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.model.Report;

import java.util.List;

public interface ReportService {

    List<Report> getReportsListByLocationsBetweenDates(List<Location> locations, String startDate, String endDate);

    List<Report> getReportsListByPlaceDetailsRatingRangeBetweenDates(List<PlaceDetail> placeDetails, Double ratingFrom, Double ratingEnd, String startDate, String endDate);

}