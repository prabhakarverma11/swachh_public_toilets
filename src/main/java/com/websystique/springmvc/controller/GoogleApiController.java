package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.service.LocationService;
import com.websystique.springmvc.service.PlaceDetailService;
import com.websystique.springmvc.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/")
public class GoogleApiController {

    private static String API_KEY = "AIzaSyCbiLp7niarzgGCy_7EesJl7-EFXlM0s14";

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceDetailService placeDetailService;

    @Autowired
    LocationService locationService;

    @RequestMapping(value = "/fetch-place-ids", method = RequestMethod.GET)
    public String fetchPlaceIds(ModelMap model) {

        List<Location> allLocations = locationService.getAllLocations();
        Gson gson = new Gson();
//        model.addAttribute("locationsListJson", gson.toJson(allLocations));
        model.addAttribute("locationsList", allLocations);

        for (Location location : allLocations) {
            String url = "https://maps.googleapis.com/maps/api/place/radarsearch/json?" +
                    "location=" +
                    location.getLatitude() + "," + location.getLongitude() +
                    "&radius=" + "10" +
                    "&type=" + "Public+Bathroom" +
                    "&key=" + API_KEY;

            try {
                Place place = placeService.fetchPlaceIdByLocation(location, url);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "locationslist";
    }

    @RequestMapping(value = "/fetch-place-details", method = RequestMethod.GET)
    public String fetchPlaceDetails(ModelMap model) {

        List<Place> allPlaces = placeService.getAllPlaces();
        model.addAttribute("placesList", allPlaces);

        for (Place place : allPlaces) {
            String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                    "placeid=" + place.getPlaceId() +
                    "&key=" + "AIzaSyDE6hMNFUb_MgX1aVvtnpAu52mS_9pZ_Zs";

            try {
                PlaceDetail placeDetail = placeDetailService.fetchPlaceDetailByPlace(place, url);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "placeslist";
    }

}