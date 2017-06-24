package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Location;

import java.util.List;


public interface LocationService {

    List<Location> getAllLocations();

    Location getLocationById(Integer locationId);
}