package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.PlaceDao;
import com.websystique.springmvc.dao.PlaceDetailDao;
import com.websystique.springmvc.dao.ReviewDao;
import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;
import com.websystique.springmvc.model.Report;
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
            Long reviewsCount = null;
            try {
                reviewsCount = reviewDao.countReviewsByPlaceBetweenDates(place, startDate, endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            report.setReviewsCount(reviewsCount);

            reportsList.add(report);
        }
        return reportsList;
    }
}
