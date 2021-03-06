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

    @Override
    public List<Location> getAllLocationsByTypePageAndSize(String locationType, Integer page, Integer size) {
        return locationDao.getAllLocationsByTypePageAndSize(locationType, page, size);
    }

    @Override
    public List<String> getLocationTypes() {
        return locationDao.getLocationTypes();
    }

    @Override
    public void update(Location location) {
        locationDao.update(location);
    }
}
