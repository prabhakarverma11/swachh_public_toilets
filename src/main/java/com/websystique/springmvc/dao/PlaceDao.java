package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaceDao {
    void save(Place place);

    Place getPlaceByLocation(Location location);

    List<Place> getAllPlaces();

    List<Place> getAllPlacesByPageAndSize(Integer page, Integer size);
}

