package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;

import java.io.IOException;
import java.util.List;

public interface PlaceDetailService {

    String fetchPlaceDetailByPlace(Place place, String url) throws IOException;

    void save(PlaceDetail placeDetail);

    PlaceDetail getPlaceDetailByPlace(Place place);

    List<PlaceDetail> getAllPlaceDetailsByLocationIdsRatingRangePageAndSize(List<Integer> locationIds, Double ratingFrom, Double ratingEnd, Integer page, Integer size);

    Long countPlaceDetailsByLocationIdsAndRatingRange(List<Integer> locationIds, Double ratingFrom, Double ratingEnd);

    Long countPlaceDetails();

    Long countPlaceDetailsByRatingRange(Double ratingFrom, Double ratingTo);

}