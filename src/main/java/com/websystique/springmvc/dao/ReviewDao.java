package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Transactional
public interface ReviewDao {

    void save(Review review);

    List<Review> getReviewsByPlace(Place place);

    List<Review> getAllReviews();

    List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Double getOverallRatingByPlace(Place place);

    Double getAverageRatingByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Long countReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException;

    Long countReviewsByPlace(Place place);

    void deleteAllRecordsByPlace(Place place);

    Long countReviewsByPlaceAndRatingRangeBetweenDates(Place place, Integer ratingFrom, Integer ratingEnd, Date startDate, Date endDate);

    List<Review> getAllReviewsByPlacePageAndSizeOrderByDate(Place place, Integer page, Integer size);

    Long countReviewsByPlaceAndRating(Place place, Integer rating);

    Long countToiletsReviewedBetweenDatesByLocationIdsAndRating(List<Integer> locationIds, Date startDate, Date endDate, Integer rating);

    List<Integer> getLocationIdsByLocationIdsAndDate(List<Integer> locationIds, Date date);

    List<Object[]> getLocationIdsByLocationIdsAndBetweenDates(List<Integer> locationIds, Date startDate, Date endDate);

}

