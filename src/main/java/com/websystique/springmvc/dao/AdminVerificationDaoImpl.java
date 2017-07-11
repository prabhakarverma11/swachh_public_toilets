package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.AdminVerification;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("adminVerificationDao")
@Transactional
class AdminVerificationDaoImpl extends AbstractDao<String, AdminVerification>
        implements AdminVerificationDao {

    static final Logger logger = LoggerFactory.getLogger(AdminVerificationDaoImpl.class);

    @Override
    public void save(AdminVerification adminVerification) {
        persist(adminVerification);
    }

    @Override
    public List<AdminVerification> getAllAdminVerificationByPageAndSize(Integer page, Integer size) {
        Criteria criteria = createEntityCriteria().setFirstResult((page - 1) * size).setMaxResults(size);
        return criteria.list();
    }

    @Override
    public AdminVerification getAdminVerificationById(Integer id) {
        return (AdminVerification) getSession().get(AdminVerification.class, id);
    }
}
