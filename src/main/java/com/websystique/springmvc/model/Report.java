package com.websystique.springmvc.model;

import java.io.Serializable;
import java.util.List;

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

    private List<Review> reviews;

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
