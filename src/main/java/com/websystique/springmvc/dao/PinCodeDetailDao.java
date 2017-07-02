package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.PinCodeDetail;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PinCodeDetailDao {

    void save(PinCodeDetail pinCodeDetail);

    PinCodeDetail getPinCodeDetailByPinCode(Integer pinCode);

    void update(PinCodeDetail pinCodeDetail);
}

