package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Location;

import java.util.List;

public interface LocationDao {

    List<Location> getAllLocations();

    Location getLocationById(Integer locationId);
}

