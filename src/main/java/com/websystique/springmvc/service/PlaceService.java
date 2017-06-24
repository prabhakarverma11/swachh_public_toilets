package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;

import java.io.IOException;
import java.util.List;

public interface PlaceService {
    public Place fetchPlaceIdByLocation(Location location, String url) throws IOException;

    void save(Place place);

    Place getPlaceByLocation(Location location);

    List<Place> getAllPlaces();
}