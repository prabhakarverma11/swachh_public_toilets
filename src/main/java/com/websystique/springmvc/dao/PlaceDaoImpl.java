package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Location;
import com.websystique.springmvc.model.Place;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("placeIdsDao")
public class PlaceDaoImpl extends AbstractDao<Integer, Place> implements PlaceDao {

    static final Logger logger = LoggerFactory.getLogger(PlaceDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Place place) {
        persist(place);
    }

    @Override
    public Place getPlaceByLocation(Location location) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("location", location));
        List<Place> placeList = criteria.list();
        if (placeList.size() > 0)
            return placeList.get(0);
        else
            return null;
    }

    @Override
    public List<Place> getAllPlaces() {
        Criteria criteria = createEntityCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Place> places = (List<Place>) criteria.list();
        return places;
    }

    @Override
    public List<Place> getAllPlacesByPageAndSize(Integer page, Integer size) {
        Criteria criteria = createEntityCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.setFirstResult((page - 1) * size).setMaxResults(size);
        List<Place> places = (List<Place>) criteria.list();
        return places;
    }
}
