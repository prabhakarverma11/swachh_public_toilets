package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.model.Report;

import java.text.ParseException;
import java.util.List;

public interface ReportService {

    List<Report> getReportsListByLocationsBetweenDates(List<Location> locations, String startDate, String endDate);

    List<Report> getReportsListByPlaceDetailsBetweenDates(List<PlaceDetail> placeDetails, String startDate, String endDate);

    List<Report> getReportsListByLocationIdsBetweenDates(List<Integer> locationIds, String startDate, String endDate, Double ratingFrom, Double ratingEnd, Integer page, Integer size) throws ParseException;

    Long countReportsListByLocationIdsBetweenDates(List<Integer> locationIds, String startDate, String endDate, Integer page, Integer size);

}