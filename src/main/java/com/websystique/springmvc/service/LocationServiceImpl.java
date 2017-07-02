package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.LocationDao;
import com.websystique.springmvc.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public List<Location> getAllLocationsByPageAndSize(Integer page, Integer size) {
        return locationDao.getAllLocationsByPageAndSize(page, size);
    }

    @Override
    public Location getLocationById(Integer locationId) {
        return locationDao.getLocationById(locationId);
    }
}
