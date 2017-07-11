package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.AdminVerificationDao;
import com.websystique.springmvc.model.AdminVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("adminVerificationService")
@Transactional
public class AdminVerificationServiceImpl implements AdminVerificationService {

    @Autowired
    AdminVerificationDao adminVerificationDao;

    @Override
    public void save(AdminVerification adminVerification) {
        adminVerificationDao.save(adminVerification);
    }
}
