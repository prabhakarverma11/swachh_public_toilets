package com.websystique.springmvc.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.websystique.springmvc.dao.PlaceDetailDao;
import com.websystique.springmvc.dao.PlacePhotoMapDao;
import com.websystique.springmvc.dao.PlaceULBMapDao;
import com.websystique.springmvc.dao.ReviewDao;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.utils.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;


@Service("placeDetailService")
//@Transactional
public class PlaceDetailServiceImpl implements PlaceDetailService {

    @Autowired
    PlaceDetailDao placeDetailDao;

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    PlaceULBMapDao placeULBMapDao;

    @Autowired
    PlacePhotoMapDao placePhotoMapDao;

    @Override
    public PlaceDetail fetchPlaceDetailByPlace(Place place, String url) throws IOException {
        System.out.println("\nplace = [" + place.getId() + "], url = [" + url + "]\n");
        UtilMethods utilMethods = new UtilMethods();
        JsonObject jsonObject = utilMethods.getJsonObjectFetchingURL(url);

        switch (jsonObject.get("status").getAsString()) {
            case "OK": {

                //saving place details
                PlaceDetail placeDetail = new PlaceDetail();
                placeDetail.setPlace(place);
                placeDetail.setAddress(jsonObject.get("result").getAsJsonObject().get("vicinity").getAsString());
                placeDetail.setName(jsonObject.get("result").getAsJsonObject().get("name").getAsString());
                placeDetail.setReference(jsonObject.get("result").getAsJsonObject().get("reference").getAsString());
                placeDetail.setUrl(jsonObject.get("result").getAsJsonObject().get("url").getAsString());
                JsonElement jsonElementRating = jsonObject.get("result").getAsJsonObject().get("rating");
                if (jsonElementRating != null)
                    placeDetail.setRating(jsonElementRating.getAsInt());

                PlaceDetail existingPlaceDetail = placeDetailDao.getPlaceDetailByPlace(place);
//                System.out.println("place = [" + place.getId() + "], url = [" + url + "]");
                if (existingPlaceDetail == null) {
                    placeDetailDao.save(placeDetail);
//                    System.out.println("placeDetail saved Successfully");
                } else {
                    if (placeDetail.getRating() != existingPlaceDetail.getRating()) {
                        placeDetail.setId(existingPlaceDetail.getId());
                        placeDetailDao.update(placeDetail);
//                        System.out.println("placeDetail updated Successfully");
                    } else {
//                        System.out.println("placeDetail is already up-to-date");
                    }
                }

                //saving reviews
                JsonArray jsonArray = jsonObject.get("result").getAsJsonObject().getAsJsonArray("reviews");
                if (jsonArray != null) {
                    reviewDao.deleteAllRecordsByPlace(place);
                    for (JsonElement jsonElement : jsonArray) {
                        Review review = new Review();

                        review.setPlace(place);
                        if (jsonElement.getAsJsonObject().get("author_name") != null)
                            review.setAuthorName(jsonElement.getAsJsonObject().get("author_name").getAsString());
                        if (jsonElement.getAsJsonObject().get("author_url") != null)
                            review.setAuthorURL(jsonElement.getAsJsonObject().get("author_url").getAsString());
                        if (jsonElement.getAsJsonObject().get("profile_photo_url") != null)
                            review.setProfilePhotoURL(jsonElement.getAsJsonObject().get("profile_photo_url").getAsString());
                        review.setRating(jsonElement.getAsJsonObject().get("rating").getAsInt());
                        if (jsonElement.getAsJsonObject().get("text") != null)
                            review.setReviewText(jsonElement.getAsJsonObject().get("text").getAsString());
                        review.setTimeStamp(new Date(jsonElement.getAsJsonObject().get("time").getAsLong() * 1000));

                        reviewDao.save(review);
//                        System.out.println("place id= [" + place.getId() + "], url = [" + url + "]");
//                        System.out.println("review saved successfully");
                    }
                }

                if (placeULBMapDao.getPlaceULBMapByPlace(place) == null) {

                    //saving photos of this place
                    JsonArray jsonArrayPhotos = jsonObject.get("result").getAsJsonObject().getAsJsonArray("photos");
                    if (jsonArrayPhotos != null) {
                        for (JsonElement jsonElement : jsonArrayPhotos) {
                            PlacePhotoMap placePhotoMap = new PlacePhotoMap();
                            placePhotoMap.setPlace(place);
                            placePhotoMap.setPhotoURL(jsonElement.getAsJsonObject().get("photo_reference").getAsString());
                            placePhotoMapDao.persist(placePhotoMap);
                        }
                    }

                    //saving location to ULB mapping
                    JsonArray jsonArrayAddress = jsonObject.get("result").getAsJsonObject().getAsJsonArray("address_components");
                    if (jsonArrayAddress != null) {
                        for (JsonElement jsonElement : jsonArrayAddress) {
                            JsonArray jsonArrayPostalCode = jsonElement.getAsJsonObject().getAsJsonArray("types");
                            if (jsonArrayPostalCode.size() > 0) {
                                if (jsonArrayPostalCode.get(0).getAsString().equals("postal_code")) {
                                    Integer postalCode = jsonElement.getAsJsonObject().get("long_name").getAsInt();
//                                String ULBName = ulbDao.getULBByPostalCode(postalCode);
                                    //TODO
                                    PlaceULBMap placeULBMap = new PlaceULBMap();
                                    placeULBMap.setPlace(place);
                                    placeULBMap.setPostalCode(postalCode);

                                    placeULBMapDao.save(placeULBMap);

//                                placeULBMap.setULBName(ULBName);
//                                    System.out.println("place = [" + place.getId() + "], url = [" + url + "]");
//                                    System.out.println("PLACE_ULB_Map saved successfully");
                                }
                            }

                        }
                    }
                }

                return placeDetail;
//                TODO
//                return null;
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
    }

    @Override
    public void save(PlaceDetail placeDetail) {
        placeDetailDao.save(placeDetail);
    }

    @Override
    public PlaceDetail getPlaceDetailByPlace(Place place) {
        return placeDetailDao.getPlaceDetailByPlace(place);
    }

}
