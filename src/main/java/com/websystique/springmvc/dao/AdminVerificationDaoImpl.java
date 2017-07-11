package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.AdminVerification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("adminVerificationDao")
@Transactional
class AdminVerificationDaoImpl extends AbstractDao<String, AdminVerification>
        implements AdminVerificationDao {

    static final Logger logger = LoggerFactory.getLogger(AdminVerificationDaoImpl.class);

    @Override
    public void save(AdminVerification adminVerification) {
        persist(adminVerification);
    }
}
