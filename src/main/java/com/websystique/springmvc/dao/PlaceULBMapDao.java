package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceULBMap;

import java.util.List;

public interface PlaceULBMapDao {

    void save(PlaceULBMap placeULBMap);

    PlaceULBMap getPlaceULBMapByPlace(Place place);

    void update(PlaceULBMap placeULBMap);

    List<PlaceULBMap> getAllPlaceULBMapByPageAndSize(Integer page, Integer size);

    List<String> getULBList();

    List<Integer> getLocationIdsByULBNameAndLocationType(String ulbName, String locationType);

}

