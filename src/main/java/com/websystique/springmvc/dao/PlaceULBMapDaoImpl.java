package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.PlaceULBMap;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("placeULBMapDao")
@Transactional
public class PlaceULBMapDaoImpl extends AbstractDao<Integer, PlaceULBMap> implements PlaceULBMapDao {

    static final Logger logger = LoggerFactory.getLogger(PlaceULBMapDaoImpl.class);

    @Override
    public void save(PlaceULBMap placeULBMap) {
        persist(placeULBMap);
    }

    @Override
    public PlaceULBMap getPlaceULBMapByPlace(Place place) {
        List<PlaceULBMap> placeULBMaps = createEntityCriteria().add(Restrictions.eq("place", place)).list();
        if (placeULBMaps.size() > 0)
            return placeULBMaps.get(0);
        else
            return null;
    }

    @Override
    public List<PlaceULBMap> getAllPlaceULBMapByPageAndSize(Integer page, Integer size) {
        return createEntityCriteria().setFirstResult((page - 1) * size).setMaxResults(size).list();
    }

    @Override
    public List<String> getULBList() {
        return createEntityCriteria().setProjection(Projections.distinct(Projections.projectionList()
                .add(Projections.property("ULBName"), "ULBName"))).list();
    }
}
