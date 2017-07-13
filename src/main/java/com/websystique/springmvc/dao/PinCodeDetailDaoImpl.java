package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.PinCodeDetail;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("pinCodeDetailDao")
public class PinCodeDetailDaoImpl extends AbstractDao<Integer, PinCodeDetail> implements PinCodeDetailDao {

    static final Logger logger = LoggerFactory.getLogger(PinCodeDetailDaoImpl.class);

    @Override
    public void save(PinCodeDetail pinCodeDetail) {
        persist(pinCodeDetail);
    }

    @Override
    public PinCodeDetail getPinCodeDetailByPinCode(Integer pinCode) {
        List<PinCodeDetail> pinCodeDetails = createEntityCriteria().add(Restrictions.eq("pinCode", pinCode)).list();
        if (pinCodeDetails.size() > 0)
            return pinCodeDetails.get(0);
        return null;
    }
}
