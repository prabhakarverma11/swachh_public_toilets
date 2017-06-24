package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.ReviewDao;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("reviewService")
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDao reviewDao;

    @Override
    public List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) {
        return reviewDao.getReviewsByPlaceBetweenDates(place, startDate, endDate);
    }
}
