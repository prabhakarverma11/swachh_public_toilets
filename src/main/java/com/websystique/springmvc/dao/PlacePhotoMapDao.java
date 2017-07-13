package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlacePhotoMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlacePhotoMapDao {

    void persist(PlacePhotoMap placePhotoMap);

    List<PlacePhotoMap> getPlacePhotoMapByPlace(Place place);

}

