package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository("placeDetailDao")
public class PlaceDetailDaoImpl extends AbstractDao<Integer, PlaceDetail> implements PlaceDetailDao {

    static final Logger logger = LoggerFactory.getLogger(PlaceDetailDaoImpl.class);


    @Override
    public void save(PlaceDetail placeDetail) {
        persist(placeDetail);
    }

    @Override
    public PlaceDetail getPlaceDetailByPlace(Place place) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("place", place));
        List<PlaceDetail> placeDetailList = criteria.list();
        if (placeDetailList.size() > 0)
            return placeDetailList.get(0);
        else
            return null;
    }

    @Override
    public List<PlaceDetail> getAllPlaceDetailsByLocationIdsRatingRangePageAndSize(List<Integer> locationIds, Double ratingFrom, Double ratingEnd, Integer page, Integer size) {
        Criteria criteria = getSession().createCriteria(PlaceDetail.class, "placeDetail")
                .add(Restrictions.ge("rating", ratingFrom))
                .add(Restrictions.le("rating", ratingEnd))
                .createCriteria("place", "place")
                .createCriteria("place.location", "location");
        if (locationIds.size() > 0)
            criteria.add(Restrictions.in("location.id", locationIds));
        else
            return new ArrayList<>();
        criteria.setFirstResult((page - 1) * size).setMaxResults(size);
        return criteria.list();
    }

    @Override
    public Long countPlaceDetailsByLocationIdsAndRatingRange(List<Integer> locationIds, Double ratingFrom, Double ratingEnd) {
        Criteria criteria = getSession().createCriteria(PlaceDetail.class, "placeDetail")
                .add(Restrictions.ge("rating", ratingFrom))
                .add(Restrictions.le("rating", ratingEnd))
                .createCriteria("place", "place")
                .createCriteria("place.location", "location");
        if (locationIds.size() > 0)
            criteria.add(Restrictions.in("location.id", locationIds));
        else
            return (long) 0;
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long countPlaceDetails() {
        return (Long) createEntityCriteria().setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long countPlaceDetailsByRatingRange(Double ratingFrom, Double ratingTo) {
        return (Long) createEntityCriteria().add(Restrictions.ge("rating", ratingFrom))
                .add(Restrictions.le("rating", ratingTo))
                .setProjection(Projections.rowCount()).uniqueResult();
    }
}
