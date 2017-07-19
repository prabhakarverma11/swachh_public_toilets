package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.LocationDao;
import com.websystique.springmvc.dao.PlaceDao;
import com.websystique.springmvc.dao.PlaceDetailDao;
import com.websystique.springmvc.dao.ReviewDao;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.model.Report;
import com.websystique.springmvc.utils.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service("reportService")
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    PlaceDao placeDao;

    @Autowired
    PlaceDetailDao placeDetailDao;

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    PlaceULBMapService placeULBMapService;

    UtilMethods utilMethods = new UtilMethods();

    @Override
    public List<Report> getReportsListByLocationsBetweenDates(List<Location> locations, String startDate, String endDate) {
        List<Report> reportsList = new ArrayList<Report>();

//        Location location = locationService.getLocationById(locationId);
        for (Location location : locations) {
            Place place = placeDao.getPlaceByLocation(location);
            PlaceDetail placeDetail = placeDetailDao.getPlaceDetailByPlace(place);

            Report report = new Report();
            report.setLocation(location);
            report.setPlace(place);
            report.setPlaceDetail(placeDetail);
            Double averageRating = null;
            Long reviewsCount = null;
            try {
                averageRating = reviewDao.getAverageRatingByPlaceBetweenDates(place, startDate, endDate);
                reviewsCount = reviewDao.countReviewsByPlaceBetweenDates(place, startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            report.setAverageRating(averageRating);
            report.setReviewsCount(reviewsCount);

            reportsList.add(report);
        }
        return reportsList;
    }

    @Override
    public List<Report> getReportsListByPlaceDetailsBetweenDates(List<PlaceDetail> placeDetails, String startDate, String endDate) {
        List<Report> reportsList = new ArrayList<Report>();

        for (PlaceDetail placeDetail : placeDetails) {

            Report report = new Report();
            report.setPlaceDetail(placeDetail);
            report.setPlace(placeDetail.getPlace());
            report.setLocation(placeDetail.getPlace().getLocation());
            report.setPlaceULBMap(placeULBMapService.getPlaceULBMapByPlace(placeDetail.getPlace()));

            Double averageRating = null;
            Long reviewsCount = null;
            try {
                averageRating = reviewDao.getAverageRatingByPlaceBetweenDates(placeDetail.getPlace(), startDate, endDate);
                reviewsCount = reviewDao.countReviewsByPlaceBetweenDates(placeDetail.getPlace(), startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            report.setAverageRating(averageRating);
            report.setReviewsCount(reviewsCount);

            reportsList.add(report);
        }
        return reportsList;
    }

    @Override
    public List<Report> getReportsListBetweenDatesByLocationIdsRatingRangePageAndSize(List<Integer> locationIds, String startDate, String endDate, Double ratingFrom, Double ratingEnd, Integer page, Integer size) throws ParseException {
        List<Report> reportsList = new ArrayList<>();

        List<Location> locations = new ArrayList<>();

        if (ratingFrom == 0.0 && ratingEnd == 5.0) {
            List<PlaceDetail> placeDetails = placeDetailDao.getAllPlaceDetailsByLocationIdsRatingRangePageAndSize(locationIds, ratingFrom, ratingEnd, page, size);
            for (PlaceDetail placeDetail : placeDetails) {
                locations.add(placeDetail.getPlace().getLocation());
            }
        } else {
            List<Object[]> toiletsReviewed = reviewDao.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, utilMethods.formatStartDate(startDate), utilMethods.formatEndDate(endDate));
            for (Object[] obj : toiletsReviewed) {
                Double rating = (Double) obj[1];
                if (ratingEnd == 5.0) {
                    if (rating >= ratingFrom && rating <= ratingEnd) {
                        Integer placeId = (Integer) obj[0];
                        locations.add(placeDao.getPlaceById(placeId).getLocation());
                    }
                } else {
                    if (rating >= ratingFrom && rating < ratingEnd) {
                        Integer placeId = (Integer) obj[0];
                        locations.add(placeDao.getPlaceById(placeId).getLocation());
                    }
                }
            }
            //pagination start

            locations = new ArrayList<>(locations.subList((page - 1) * size < locations.size() ? (page - 1) * size : 0, page * size < locations.size() ? page * size : locations.size()));
            //pagination end
        }
        for (Location location : locations) {

            Report report = new Report();
            report.setLocation(location);
            report.setPlace(placeDao.getPlaceByLocation(location));
            report.setPlaceDetail(placeDetailDao.getPlaceDetailByPlace(report.getPlace()));
            report.setPlaceULBMap(placeULBMapService.getPlaceULBMapByPlace(report.getPlace()));

            Double averageRating = null;
            Long reviewsCount = null;
            try {
                averageRating = reviewDao.getAverageRatingByPlaceBetweenDates(report.getPlace(), startDate, endDate);
                reviewsCount = reviewDao.countReviewsByPlaceBetweenDates(report.getPlace(), startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            report.setAverageRating(averageRating);
            report.setReviewsCount(reviewsCount);

            reportsList.add(report);
        }
        return reportsList;
    }

    @Override
    public Long countReportsListBetweenDatesByLocationIdsAndRatingRange(List<Integer> locationIds, String startDate, String endDate, Double ratingFrom, Double ratingEnd) throws ParseException {
        if (ratingFrom == 0.0 && ratingEnd == 5.0) {
            return placeDetailDao.countPlaceDetailsByLocationIdsAndRatingRange(locationIds, ratingFrom, ratingEnd);
        } else {
            List<Object[]> toiletsReviewed = reviewDao.getPlaceIdsByLocationIdsAndBetweenDates(locationIds, utilMethods.formatStartDate(startDate), utilMethods.formatEndDate(endDate));
            Long count = 0L;
            for (Object[] obj : toiletsReviewed) {
                Double rating = (Double) obj[1];
                if (ratingEnd == 5.0) {
                    if (rating >= ratingFrom && rating <= ratingEnd) {
                        count++;
                    }
                } else {
                    if (rating >= ratingFrom && rating < ratingEnd) {
                        count++;
                    }
                }
            }
            return count;
        }
    }
}
