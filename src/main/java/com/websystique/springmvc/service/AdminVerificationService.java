package com.websystique.springmvc.service;

import com.websystique.springmvc.model.AdminVerification;

import java.util.List;

public interface AdminVerificationService {

    void save(AdminVerification adminVerification);

    List<AdminVerification> getAllAdminVerificationByPageAndSize(Integer page, Integer size);

}