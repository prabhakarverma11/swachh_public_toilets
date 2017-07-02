package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceULBMap;

import java.util.List;

public interface PlaceULBMapService {

    void save(PlaceULBMap placeULBMap);

    PlaceULBMap getPlaceULBMapByPlace(Place place);

    void update(PlaceULBMap placeULBMap);

    List<PlaceULBMap> getAllPlaceULBMapByPageAndSize(Integer page, Integer size);

}