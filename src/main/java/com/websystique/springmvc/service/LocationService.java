package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Location;

import java.util.List;


public interface LocationService {

    List<Location> getAllLocations();

    List<Location> getAllLocationsByPageAndSize(Integer page, Integer size);

    Location getLocationById(Integer locationId);

    List<Location> getAllLocationsByTypePageAndSize(String locationType, Integer page, Integer size);

    List<String> getLocationTypes();

}