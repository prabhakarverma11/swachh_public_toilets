package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository("reviewDao")
public class ReviewDaoImpl extends AbstractDao<Integer, Review> implements ReviewDao {

    @Autowired
    private SessionFactory sessionFactory;

    static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Override
    public void save(Review review) {
        persist(review);
    }

    @Override
    public List<Review> getReviewsByPlace(Place place) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("place", place));
        List<Review> reviews = criteria.list();
        return reviews;
    }

    @Override
    public List<Review> getAllReviews() {
        Criteria criteria = createEntityCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Review> reviews = criteria.list();
        return reviews;
    }

    @Override
    public List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) {

        Criteria criteria = createEntityCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (place != null)
            criteria = criteria.add(Restrictions.eq("place", place));

        if (startDate != null)
            criteria = criteria.add(Restrictions.ge("timeStamp", new Date(startDate)));

        if (endDate != null)
            criteria = criteria.add(Restrictions.le("timeStamp", new Date(endDate)));

        List<Review> reviews = criteria.list();
        return reviews;
    }
}
