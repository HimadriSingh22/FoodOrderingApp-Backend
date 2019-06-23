package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentBusinessService {
    @Autowired
    PaymentDao paymentDao;
    public PaymentEntity getPaymentByUUID(String payment_uuid)throws PaymentMethodNotFoundException
    {
        PaymentEntity paymentEntity = paymentDao.getPaymentByUUID(payment_uuid);
        if(paymentEntity.equals(""))
        {
            throw new PaymentMethodNotFoundException("PNF-002","No payment method found by this id");
        }
        return paymentEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<PaymentEntity> getPaymentMethods()
    {
       return paymentDao.getPaymentMethods();
    }
}
