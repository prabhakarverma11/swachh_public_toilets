package com.websystique.springmvc.service;

import com.websystique.springmvc.model.PinCodeDetail;

import java.util.List;


public interface PinCodeDetailService {

    List<PinCodeDetail> getAllPinCodeDetails();

    List<PinCodeDetail> getAllPinCodeDetailsByPageAndSize(Integer page, Integer size);

    PinCodeDetail getPinCodeDetailById(Integer pinCodeDetailId);

    PinCodeDetail getPinCodeDetailByPinCode(Integer pinCode);
}