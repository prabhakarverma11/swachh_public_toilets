package com.websystique.springmvc.service;

import com.websystique.springmvc.dao.PinCodeDetailDao;
import com.websystique.springmvc.model.PinCodeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("pinCodeDetailService")
@Transactional
public class PinCodeDetailServiceImpl implements PinCodeDetailService {

    @Autowired
    PinCodeDetailDao pinCodeDetailDao;

    @Override
    public List<PinCodeDetail> getAllPinCodeDetails() {
        return null;
    }

    @Override
    public List<PinCodeDetail> getAllPinCodeDetailsByPageAndSize(Integer page, Integer size) {
        return null;
    }

    @Override
    public PinCodeDetail getPinCodeDetailById(Integer pinCodeDetailId) {
        return null;
    }

    @Override
    public PinCodeDetail getPinCodeDetailByPinCode(Integer pinCode) {
        return pinCodeDetailDao.getPinCodeDetailByPinCode(pinCode);
    }
}
