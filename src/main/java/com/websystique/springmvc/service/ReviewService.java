package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;

import java.text.ParseException;
import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Double getOverallRatingByPlace(Place place);

    Double getAverageRatingByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Long countReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Long countReviewsByPlace(Place place);

    List<Review> getAllReviewsByPlacePageAndSizeOrderByDate(Place place, Integer page, Integer size);

}