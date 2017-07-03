package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;

import java.io.IOException;
import java.util.List;

public interface PlaceDetailService {

    PlaceDetail fetchPlaceDetailByPlace(Place place, String url) throws IOException;

    void save(PlaceDetail placeDetail);

    PlaceDetail getPlaceDetailByPlace(Place place);

    List<PlaceDetail> getAllPlaceDetailsByLocationTypeRatingRangePageAndSize(String locationType, Double ratingFrom, Double ratingEnd, Integer page, Integer size);

    Long countPlaceDetailsByLocationTypeAndRatingRange(String locationType, Double ratingFrom, Double ratingEnd);

    Long countPlaceDetails();

    Long countPlaceDetailsByRatingRange(Double ratingFrom, Double ratingTo);
}