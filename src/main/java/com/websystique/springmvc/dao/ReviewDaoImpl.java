package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {

        Criteria criteria = createEntityCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (place != null)
            criteria = criteria.add(Restrictions.eq("place", place));

        if (startDate != null && !startDate.equals(""))
            criteria = criteria.add(Restrictions.ge("timeStamp", new SimpleDateFormat("yyyy-MM-dd").parse(startDate)));

        if (endDate != null && !endDate.equals(""))
            criteria = criteria.add(Restrictions.le("timeStamp", new SimpleDateFormat("yyyy-MM-dd").parse(endDate)));

        List<Review> reviews = criteria.list();
        return reviews;
    }

    @Override
    public Double getOverallRatingByPlace(Place place) {
        Criteria criteria = createEntityCriteria();

        if (place != null)
            criteria = criteria.add(Restrictions.eq("place", place));

        criteria.setProjection(Projections.avg("rating"));
        return (Double) criteria.uniqueResult();
    }

    @Override
    public Double getAverageRatingByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {
        Criteria criteria = createEntityCriteria();

        if (place != null)
            criteria = criteria.add(Restrictions.eq("place", place));

        if (startDate != null && !startDate.equals(""))
            criteria = criteria.add(Restrictions.ge("timeStamp", new SimpleDateFormat("yyyy-MM-dd").parse(startDate)));

        if (endDate != null && !endDate.equals(""))
            criteria = criteria.add(Restrictions.le("timeStamp", new SimpleDateFormat("yyyy-MM-dd").parse(endDate)));

        criteria.setProjection(Projections.avg("rating"));
        return (Double) criteria.uniqueResult();
    }

    @Override
    public Long countReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {
        Criteria criteria = createEntityCriteria();

        if (place != null)
            criteria = criteria.add(Restrictions.eq("place", place));

        if (startDate != null && !startDate.equals(""))
            criteria = criteria.add(Restrictions.ge("timeStamp", new SimpleDateFormat("yyyy-MM-dd").parse(startDate)));

        if (endDate != null && !endDate.equals(""))
            criteria = criteria.add(Restrictions.le("timeStamp", new SimpleDateFormat("yyyy-MM-dd").parse(endDate)));

        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public Long countReviewsByPlace(Place place) {
        Criteria criteria = createEntityCriteria();
        if (place != null)
            criteria.add(Restrictions.eq("place", place));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public void deleteAllRecordsByPlace(Place place) {
        String hql = "delete from Review where place= :place";
        Query query = getSession().createQuery(hql).setParameter("place", place);
        int result = query.executeUpdate();
        System.out.println("Rows affected: " + result);
    }
}
