package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.AdminVerification;

import java.util.List;

public interface AdminVerificationDao {

    void save(AdminVerification adminVerification);

    List<AdminVerification> getAllAdminVerificationByPageAndSize(Integer page, Integer size);

    AdminVerification getAdminVerificationById(Integer id);

    void delete(AdminVerification adminVerification);

}

