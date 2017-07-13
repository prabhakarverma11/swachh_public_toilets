package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.AdminVerificationDao;
import com.websystique.springmvc.model.AdminVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("adminVerificationService")
@Transactional
public class AdminVerificationServiceImpl implements AdminVerificationService {

    @Autowired
    AdminVerificationDao adminVerificationDao;

    @Override
    public void save(AdminVerification adminVerification) {
        adminVerificationDao.save(adminVerification);
    }

    @Override
    public List<AdminVerification> getAllAdminVerificationByPageAndSize(Integer page, Integer size) {
        return adminVerificationDao.getAllAdminVerificationByPageAndSize(page, size);
    }

    @Override
    public AdminVerification getAdminVerificationById(Integer id) {
        return adminVerificationDao.getAdminVerificationById(id);
    }

    @Override
    public void delete(AdminVerification adminVerification) {
        adminVerificationDao.delete(adminVerification);
    }
}
