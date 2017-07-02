package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlaceDetailDao {

    void save(PlaceDetail placeDetail);

    PlaceDetail getPlaceDetailByPlace(Place place);

    void update(PlaceDetail placeDetail);
}

