package com.websystique.springmvc.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.websystique.springmvc.dao.PlaceDetailDao;
import com.websystique.springmvc.dao.ReviewDao;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.model.Review;
import com.websystique.springmvc.utils.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;


@Service("placeDetailService")
@Transactional
public class PlaceDetailServiceImpl implements PlaceDetailService {

    @Autowired
    PlaceDetailDao placeDetailDao;

    @Autowired
    ReviewDao reviewDao;

    @Override
    public PlaceDetail fetchPlaceDetailByPlace(Place place, String url) throws IOException {
        UtilMethods utilMethods = new UtilMethods();
        JsonObject jsonObject = utilMethods.getJsonObjectFetchingURL(url);

        if (jsonObject.get("status").getAsString().equals("OK")) {
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
            System.out.println("place = [" + place.getPlaceId() + "], url = [" + url + "]");
            if (existingPlaceDetail == null) {
                placeDetailDao.save(placeDetail);
                System.out.println("placeDetail saved Successfully");
            } else {
                if (placeDetail.getRating() != existingPlaceDetail.getRating()) {
                    placeDetailDao.update(placeDetail);
                    System.out.println("placeDetail updated Successfully");
                } else {
                    System.out.println("placeDetail is already up-to-date");
                }
            }


            JsonArray jsonArray = jsonObject.get("result").getAsJsonObject().getAsJsonArray("reviews");
            if (jsonArray != null) {
                reviewDao.deleteAllRecordsByPlace(place);
                for (JsonElement jsonElement : jsonArray) {
                    Review review = new Review();

                    review.setPlace(place);
                    review.setAuthorName(jsonElement.getAsJsonObject().get("author_name").getAsString());
                    review.setAuthorURL(jsonElement.getAsJsonObject().get("author_url").getAsString());
                    review.setProfilePhotoURL(jsonElement.getAsJsonObject().get("profile_photo_url").getAsString());
                    review.setRating(jsonElement.getAsJsonObject().get("rating").getAsInt());
                    review.setReviewText(jsonElement.getAsJsonObject().get("text").getAsString());
                    review.setTimeStamp(new Date(jsonElement.getAsJsonObject().get("time").getAsLong() * 1000));

                    reviewDao.save(review);
                    System.out.println("place = [" + place + "], url = [" + url + "]");
                    System.out.println("review saved successfully");
                }
            }
            return placeDetail;
        }
        //print result
        return null;
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
