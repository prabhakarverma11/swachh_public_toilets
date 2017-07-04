package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.PlaceULBMapDao;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceULBMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("placeULBMapService")
@Transactional
public class PlaceULBMapServiceImpl implements PlaceULBMapService {

    @Autowired
    PlaceULBMapDao placeULBMapDao;

    @Override
    public void save(PlaceULBMap placeULBMap) {
        placeULBMapDao.save(placeULBMap);
    }

    @Override
    public PlaceULBMap getPlaceULBMapByPlace(Place place) {
        return placeULBMapDao.getPlaceULBMapByPlace(place);
    }

    @Override
    public void update(PlaceULBMap placeULBMap) {
        placeULBMapDao.update(placeULBMap);
    }

    @Override
    public List<PlaceULBMap> getAllPlaceULBMapByPageAndSize(Integer page, Integer size) {
        return placeULBMapDao.getAllPlaceULBMapByPageAndSize(page, size);
    }

    @Override
    public List<String> getULBList() {
        return placeULBMapDao.getULBList();
    }

    @Override
    public List<Integer> getLocationIdsByULBNameAndLocationType(String ulbName, String locationType) {
        return placeULBMapDao.getLocationIdsByULBNameAndLocationType(ulbName, locationType);
    }
}
