package com.websystique.springmvc.model;

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

    private String reviewsJson;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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

    public String getReviews() {
        return reviewsJson;
    }

    public void setReviews(String reviewsJson) {
        this.reviewsJson = reviewsJson;
    }
}
