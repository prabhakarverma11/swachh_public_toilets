package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Location;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("locationDao")
public class LocationDaoImpl extends AbstractDao<Integer, Location> implements LocationDao {

    static final Logger logger = LoggerFactory.getLogger(LocationDaoImpl.class);

    @Override
    public List<Location> getAllLocations() {
        Criteria criteria = createEntityCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Location> locations = (List<Location>) criteria.list();
        return locations;
    }

    @Override
    public List<Location> getAllLocationsByPageAndSize(Integer page, Integer size) {
        Criteria criteria = createEntityCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.

        criteria.setFirstResult((page - 1) * size).setMaxResults(size);

        List<Location> locations = (List<Location>) criteria.list();
        return locations;
    }

    @Override
    public List<Location> getAllLocationsByTypePageAndSize(String locationType, Integer page, Integer size) {
        return createEntityCriteria().add(Restrictions.eq("type", locationType))
                .setFirstResult((page - 1) * size).setMaxResults(size).list();
    }

    @Override
    public List<String> getLocationTypes() {
        return createEntityCriteria().setProjection(Projections.distinct(Projections.projectionList()
                .add(Projections.property("type"), "type"))).list();
    }

    @Override
    public Location getLocationById(Integer locationId) {
        return getByKey(locationId);
    }

}
