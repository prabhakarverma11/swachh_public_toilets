package com.websystique.springmvc.model;

import org.apache.commons.lang3.text.WordUtils;

import java.io.Serializable;

/**
 * Created by prabhakar on 24/6/17.
 */

/**
 *
 *
 */
//@Entity
public class Report implements Serializable {

    private Location location;

    private Place place;

    private PlaceDetail placeDetail;

    private Long reviewsCount;

    private Double averageRating;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        location.setAddress(WordUtils.capitalize(location.getAddress()));
        this.location = location;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public PlaceDetail getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(PlaceDetail placeDetail) {
        this.placeDetail = placeDetail;
    }

    public Long getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Long reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
