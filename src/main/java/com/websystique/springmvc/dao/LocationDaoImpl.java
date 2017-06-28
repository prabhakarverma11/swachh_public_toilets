package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Location;
import org.hibernate.Criteria;
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

        criteria.setFirstResult(0).setMaxResults(20);

        List<Location> locations = (List<Location>) criteria.list();
        return locations;
    }

    @Override
    public Location getLocationById(Integer locationId) {
        return getByKey(locationId);
    }
}
