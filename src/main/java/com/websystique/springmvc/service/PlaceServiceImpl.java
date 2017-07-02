package com.websystique.springmvc.service;

import com.google.gson.JsonObject;
import com.websystique.springmvc.dao.PlaceDao;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.utils.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service("placeIdsService")
//@Transactional
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceDao placeDao;

    @Override
    public Place fetchPlaceIdByLocation(Location location, String url) throws IOException {
        System.out.println("location = [" + location.getName() + "], url = [" + url + "]");

        if (placeDao.getPlaceByLocation(location) == null) {
            UtilMethods utilMethods = new UtilMethods();
            JsonObject jsonObject = utilMethods.getJsonObjectFetchingURL(url);
            if (jsonObject != null) {
                switch (jsonObject.get("status").getAsString()) {
                    case "OK": {
                        Place place = new Place();
                        place.setLocation(location);
                        String placeIdValue = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("place_id").getAsString();
                        place.setPlaceId(placeIdValue);
                        placeDao.save(place);

                        System.out.println("Place saved successfully");

                        return place;
                    }
                    case "ZERO_RESULTS": {
                        System.out.println("\nZERO_RESULTS\n");
                        return null;
                    }
                    case "OVER_QUERY_LIMIT": {
                        System.out.println("\nOVER_QUERY_LIMIT\n");
                        return null;
                    }
                    case "REQUEST_DENIED": {
                        System.out.println("\nREQUEST_DENIED\n");
                        return null;
                    }
                    case "INVALID_REQUEST": {
                        System.out.println("\nINVALID_REQUEST\n");
                        return null;
                    }
                    default: {
                        System.out.println("\nDOESN'T KNOW WHAT HAPPENED\n");
                        return null;
                    }
                }
            } else {

            }
        } else {
            System.out.println("place_id already there in database");
        }
        return null;
        //print result
    }

    @Override
    public void save(Place place) {
        placeDao.save(place);
    }

    @Override
    public Place getPlaceByLocation(Location location) {
        return placeDao.getPlaceByLocation(location);
    }

    @Override
    public List<Place> getAllPlaces() {
        return placeDao.getAllPlaces();
    }

    @Override
    public List<Place> getAllPlacesByPageAndSize(Integer page, Integer size) {
        return placeDao.getAllPlacesByPageAndSize(page, size);
    }
}
