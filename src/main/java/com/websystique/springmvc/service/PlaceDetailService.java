package com.websystique.springmvc.service;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;

import java.io.IOException;
import java.net.MalformedURLException;

public interface PlaceDetailService {

    PlaceDetail fetchPlaceDetailByPlace(Place place, String url) throws IOException;

    void save(PlaceDetail placeDetail);

    PlaceDetail getPlaceDetailByPlace(Place place);

}