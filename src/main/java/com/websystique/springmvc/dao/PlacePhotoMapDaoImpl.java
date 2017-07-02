package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlacePhotoMap;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("placePhotoMapDao")
public class PlacePhotoMapDaoImpl extends AbstractDao<Integer, PlacePhotoMap> implements PlacePhotoMapDao {

    static final Logger logger = LoggerFactory.getLogger(PlacePhotoMapDaoImpl.class);

    @Override
    public List<PlacePhotoMap> getPlacePhotoMapByPlace(Place place) {
        return createEntityCriteria().add(Restrictions.eq("place", place)).list();
    }
}
