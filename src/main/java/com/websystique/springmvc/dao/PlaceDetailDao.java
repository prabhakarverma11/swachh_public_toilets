package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaceDetailDao {

    void save(PlaceDetail placeDetail);

    PlaceDetail getPlaceDetailByPlace(Place place);

    void update(PlaceDetail placeDetail);

    List<PlaceDetail> getAllPlaceDetailsByLocationTypeRatingRangePageAndSize(String locationType, Double ratingFrom, Double ratingEnd, Integer page, Integer size);

    Long countPlaceDetailsByLocationTypeAndRatingRange(String locationType, Double ratingFrom, Double ratingEnd);

    Long countPlaceDetails();

    Long countPlaceDetailsByRatingRange(Double ratingFrom, Double ratingTo);

}

