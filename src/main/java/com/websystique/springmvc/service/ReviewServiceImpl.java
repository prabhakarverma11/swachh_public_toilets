package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.ReviewDao;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
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
    public List<Review> getAllReviewsByPlacePageAndSizeOrderByDate(Place place,Integer page,Integer size) {
        return reviewDao.getAllReviewsByPlacePageAndSizeOrderByDate(place, page,size);
    }
}
