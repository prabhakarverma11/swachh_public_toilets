package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Location;

import java.util.List;

public interface LocationDao {

    List<Location> getAllLocations();

    Location getLocationById(Integer locationId);

    List<Location> getAllLocationsByPageAndSize(Integer page, Integer size);

    List<Location> getAllLocationsByTypePageAndSize(String locationType, Integer page, Integer size);

}

