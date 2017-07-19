package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Place;
import com.websystique.springmvc.model.Review;
import com.websystique.springmvc.utils.UtilMethods;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Repository("reviewDao")
public class ReviewDaoImpl extends AbstractDao<Integer, Review> implements ReviewDao {

    static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    UtilMethods utilMethods = new UtilMethods();

    @Override
    public void save(Review review) {
        persist(review);
    }

    @Override
    public List<Review> getReviewsByPlace(Place place) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("place", place));
//        criteria.setFirstResult(0).setMaxResults(20);
        List<Review> reviews = criteria.list();
        return reviews;
    }

    @Override
    public List<Review> getAllReviews() {
        Criteria criteria = createEntityCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.setFirstResult(0).setMaxResults(20);
        List<Review> reviews = criteria.list();
        return reviews;
    }

    @Override
    public List<Review> getReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {

        Criteria criteria = createEntityCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (place != null)
            criteria = criteria.add(Restrictions.eq("place", place));

        if (startDate != null && !startDate.equals("") && !startDate.equals("null"))
            criteria = criteria.add(Restrictions.ge("timeStamp", utilMethods.formatStartDate(startDate)));

        if (endDate != null && !endDate.equals("") && !endDate.equals("null"))
            criteria = criteria.add(Restrictions.le("timeStamp", utilMethods.formatEndDate(endDate)));

        criteria.setFirstResult(0).setMaxResults(20);
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

        if (startDate != null && !startDate.equals("") && !startDate.equals("null"))
            criteria = criteria.add(Restrictions.ge("timeStamp", utilMethods.formatStartDate(startDate)));

        if (endDate != null && !endDate.equals("") && !endDate.equals("null"))
            criteria = criteria.add(Restrictions.le("timeStamp", utilMethods.formatEndDate(endDate)));

        criteria.setProjection(Projections.avg("rating"));
        return (Double) criteria.uniqueResult();
    }

    @Override
    public Long countReviewsByPlaceBetweenDates(Place place, String startDate, String endDate) throws ParseException {
        Criteria criteria = createEntityCriteria();

        if (place != null)
            criteria = criteria.add(Restrictions.eq("place", place));

        if (startDate != null && !startDate.equals("") && !startDate.equals("null"))
            criteria = criteria.add(Restrictions.ge("timeStamp", utilMethods.formatStartDate(startDate)));

        if (endDate != null && !endDate.equals("") && !endDate.equals("null"))
            criteria = criteria.add(Restrictions.le("timeStamp", utilMethods.formatEndDate(endDate)));

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
        String hql = "delete from Review where place.id= :placeId";
        Query query = getSession().createQuery(hql).setParameter("placeId", place.getId());
        int result = query.executeUpdate();
//        System.out.println("Rows affected: " + result);
    }

    @Override
    public Long countReviewsByPlaceAndRatingRangeBetweenDates(Place place, Integer ratingFrom, Integer ratingEnd, Date startDate, Date endDate) {
        Criteria criteria = createEntityCriteria();

        if (place != null)
            criteria.add(Restrictions.eq("place", place));

        criteria.add(Restrictions.ge("rating", ratingFrom)).add(Restrictions.le("rating", ratingEnd));

        if (startDate != null)
            criteria.add(Restrictions.ge("timeStamp", startDate));

        if (endDate != null)
            criteria.add(Restrictions.le("timeStamp", endDate));

        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public List<Review> getAllReviewsByPlacePageAndSizeOrderByDate(Place place, Integer page, Integer size) {

        return createEntityCriteria().add(Restrictions.eq("place", place))
                .setFirstResult((page - 1) * size).setMaxResults(size)
                .addOrder(Order.desc("timeStamp")).list();
    }

    @Override
    public Long countReviewsByPlaceAndRating(Place place, Integer rating) {
        return (Long) createEntityCriteria().add(Restrictions.eq("place", place))
                .add(Restrictions.eq("rating", rating))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long countToiletsReviewedBetweenDatesByLocationIdsAndRating(List<Integer> locationIds, Date startDate, Date endDate, Integer rating) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("rating", rating))
                .add(Restrictions.ge("timeStamp", startDate))
                .add(Restrictions.le("timeStamp", endDate));
        if (locationIds.size() > 0) {
            criteria.createCriteria("place", "place")
                    .createCriteria("place.location", "location")
                    .add(Restrictions.in("location.id", locationIds));
        }
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<Integer> getLocationIdsByLocationIdsAndDate(List<Integer> locationIds, Date date) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("timeStamp", date))
                .createCriteria("place", "place")
                .createCriteria("place.location", "location");
        if (locationIds.size() > 0)
            criteria.add(Restrictions.in("location.id", locationIds))
                    .setProjection(Projections.property("location.id"));
        return criteria.list();
    }

    @Override
    public List<Object[]> getPlaceIdsByLocationIdsAndBetweenDates(List<Integer> locationIds, Date startDate, Date endDate) {
        Criteria criteria = getSession().createCriteria(Review.class, "review");

        if (startDate != null)
            criteria.add(Restrictions.ge("timeStamp", startDate));
        if (endDate != null)
            criteria.add(Restrictions.le("timeStamp", endDate));

        criteria.createCriteria("place", "place").createCriteria("location", "location");
        if (locationIds.size() > 0)
            criteria.add(Restrictions.in("location.id", locationIds));

        ProjectionList list = Projections.projectionList();
        list.add(Projections.groupProperty("place.id"));
        list.add(Projections.avg("review.rating").as("rating"));
        criteria.setProjection(list);
        return criteria.list();
    }

    @Override
    public Long countReviewsByLocationIds(List<Integer> locationIds) {
        Criteria criteria = createEntityCriteria()
                .createCriteria("place", "place")
                .createCriteria("place.location", "location")
                .add(Restrictions.in("location.id", locationIds));
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

    }

    @Override
    public Long countCommentsByLocationIds(List<Integer> locationIds) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.ne("reviewText", ""))
                .createCriteria("place", "place")
                .createCriteria("place.location", "location")
                .add(Restrictions.in("location.id", locationIds));
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }
}
