package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.ReviewDao;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Service("reviewService")
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDao reviewDao;

    @Override
    public List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {
        return reviewDao.getReviewsByPlaceBetweenDates(place, startDate, endDate);
    }

    @Override
    public Double getOverallRatingByPlace(Place place) {
        return reviewDao.getOverallRatingByPlace(place);
    }

    @Override
    public Double getAverageRatingByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {
        return reviewDao.getAverageRatingByPlaceBetweenDates(place, startDate, endDate);
    }

    @Override
    public Long countReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {
        return reviewDao.countReviewsByPlaceBetweenDates(place, startDate, endDate);
    }

    @Override
    public Long countReviewsByPlace(Place place) {
        return reviewDao.countReviewsByPlace(place);
    }

    @Override
    public List<Review> getAllReviewsByPlacePageAndSizeOrderByDate(Place place, Integer page, Integer size) {
        return reviewDao.getAllReviewsByPlacePageAndSizeOrderByDate(place, page, size);
    }

    @Override
    public Long countReviewsByPlaceAndRating(Place place, Integer rating) {
        return reviewDao.countReviewsByPlaceAndRating(place, rating);
    }

    @Override
    public Long countToiletsReviewedBetweenDatesByLocationIdsAndRating(List<Integer> locationIds, Date startDate, Date endDate, Integer rating) {
        return reviewDao.countToiletsReviewedBetweenDatesByLocationIdsAndRating(locationIds, startDate, endDate, rating);
    }

    @Override
    public Long countReviewsByPlaceAndRatingRangeBetweenDates(Place place, Integer ratingFrom, Integer ratingEnd, Date startDate, Date endDate) {
        return reviewDao.countReviewsByPlaceAndRatingRangeBetweenDates(place, ratingFrom, ratingEnd, startDate, endDate);
    }

    @Override
    public List<Integer> getLocationIdsByLocationIdsAndDate(List<Integer> locationIds, Date date) {
        return reviewDao.getLocationIdsByLocationIdsAndDate(locationIds, date);
    }

    @Override
    public List<Object[]> getLocationIdsByLocationIdsAndBetweenDates(List<Integer> locationIds, Date startDate, Date endDate) {
        return reviewDao.getLocationIdsByLocationIdsAndBetweenDates(locationIds, startDate, endDate);
    }

    @Override
    public Long countReviewsByLocationIds(List<Integer> locationIds) {
        return reviewDao.countReviewsByLocationIds(locationIds);
    }

    @Override
    public Long countCommentsByLocationIds(List<Integer> locationIds) {
        return reviewDao.countCommentsByLocationIds(locationIds);
    }

}
