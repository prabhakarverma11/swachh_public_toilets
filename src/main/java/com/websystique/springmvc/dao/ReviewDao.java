package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;

import java.util.List;

public interface ReviewDao {

    void save(Review review);

    List<Review> getReviewsByPlace(Place place);

    List<Review> getAllReviews();

}

