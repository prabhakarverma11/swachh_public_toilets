package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Double getOverallRatingByPlace(Place place);

    Double getAverageRatingByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Long countReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Long countReviewsByPlace(Place place);

    List<Review> getAllReviewsByPlacePageAndSizeOrderByDate(Place place, Integer page, Integer size);

    Long countReviewsByPlaceAndRating(Place place, Integer rating);

    Long countToiletsReviewedBetweenDatesByLocationIdsAndRating(List<Integer> locationIds, Date startDate, Date endDate, Integer rating);

    Long countReviewsByPlaceAndRatingRangeBetweenDates(Place place, Integer ratingFrom, Integer ratingEnd, Date startDate, Date endDate);

    List<Integer> getLocationIdsByLocationIdsAndDate(List<Integer> locationIds, Date date);

}