package com.websystique.springmvc.controller;

import com.google.gson.Gson;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
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

    private static String[] API_KEY = {
            "AIzaSyD304cZovgQsVQt2vaAILBdD3bD5pFHcx0",
            "AIzaSyBqdMkhD5uNjfCbCrDUr5L3l0qGEgWKBFI",
            "AIzaSyBKgJqF-l_X5gywxnMpqYgO_eR8G7GiIwA",
            "AIzaSyArXKSjF_3LD9pr-vn7CYtVo9ar7wbsP54",
            "AIzaSyBu0NRIzM_NSNphZH2zTy-7tPiqrSmO4AA",
            "AIzaSyCXC3L1bLCp_sFUDxtqtrRP43uIHOfldss",
            "AIzaSyBkLfT8KwQu1N1Oncaky_O7WzcH0rdOSuI",
            "AIzaSyD6YNI_T7cKw26Oq5A_CBhQY0JhFyVFl7A",
            "AIzaSyDVhfr53ZyIV4ms0UUz9026Ip3bxtODdR4"
    };

    @Autowired
    PlaceService placeService;

    @Autowired
    PlaceDetailService placeDetailService;

    @Autowired
    LocationService locationService;

    @RequestMapping(value = "/fetch-place-ids", method = RequestMethod.GET)
    public String fetchPlaceIds(ModelMap model) {
        //TODO change it
        List<Location> allLocations = locationService.getAllLocationsByPageAndSize(26, 200);
        Gson gson = new Gson();
//        model.addAttribute("locationsListJson", gson.toJson(allLocations));
        model.addAttribute("locationsList", allLocations);

        for (Location location : allLocations) {
            String url = "https://maps.googleapis.com/maps/api/place/radarsearch/json?" +
                    "location=" +
                    location.getLatitude() + "," + location.getLongitude() +
                    "&radius=" + "10" +
                    "&type=" + "Public+Bathroom" +
                    "&key=" + API_KEY[7];

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
        //TODO change it
        List<Place> allPlaces = placeService.getAllPlacesByPageAndSize(3, 2472);
        model.addAttribute("placesList", allPlaces);

        for (Place place : allPlaces) {
            String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                    "placeid=" + place.getPlaceId() +
                    "&key=" + API_KEY[7];

            try {
                String response = placeDetailService.fetchPlaceDetailByPlace(place, url);
                System.out.println("Done with place id: " + place.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "placeslist";
    }

}