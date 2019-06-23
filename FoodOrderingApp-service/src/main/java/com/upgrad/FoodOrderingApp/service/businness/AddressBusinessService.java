package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AddressBusinessService {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private StateDao stateDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(String accessToken , AddressEntity addressEntity)throws AddressNotFoundException,SaveAddressException, AuthorizationFailedException
    {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(accessToken);
        if(customerAuthEntity==null)
        {
            throw new AuthorizationFailedException("ATH-001","Customer is not logged in!");
        }

        final ZonedDateTime now = ZonedDateTime.now();
        if(customerAuthEntity.getLogoutAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATH-002","Customer is logged out ! Login again to access this endpoint!");

        }
        if(customerAuthEntity.getExpiresAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATH-003","Your session is expired.Log in again to access this endpoint!");
        }
        else{
            //checking if any field is empty
            if(addressEntity.getFlat_buil_number()==null||addressEntity.getCity()==null||addressEntity.getLocality()==null||addressEntity.getPincode()==null|| addressEntity.getState_id()==null)
            {
                throw new SaveAddressException("SAR-001","No field can be empty!!");
            }
            //checking if pincode is invalid
            boolean isValid = pinIsValid(addressEntity.getPincode());
            if(isValid==false)
            {
                throw new SaveAddressException("SAR-002","Invalid Pincode!!");
            }
            //checking the state_id
            StateEntity stateEntity = addressDao.checkState(addressEntity.getState_id().toString());
            if(stateEntity==null)
            {
                throw new AddressNotFoundException("ANF-002","No state by this id!");
            }

            AddressEntity savedAddress = addressDao.saveAddress(addressEntity);
            return savedAddress;
        }
    }

    public boolean pinIsValid(String pinCode)
    {
        boolean p=true;
        if(pinCode.length()==6)
        {
        Pattern pattern = Pattern.compile("[0-9]");
         p = pattern.matcher(pinCode).find();
        }
        return p;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List showAddressList(String accessToken)throws AuthorizationFailedException
    {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(accessToken);
        if(customerAuthEntity==null)
        {
            throw new AuthorizationFailedException("ATH-001","Customer is not logged in!");
        }

        final ZonedDateTime now = ZonedDateTime.now();
        if(customerAuthEntity.getLogoutAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATH-002","Customer is logged out ! Login again to access this endpoint!");

        }
        if(customerAuthEntity.getExpiresAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATH-003","Your session is expired.Log in again to access this endpoint!");
        }
        else{
            List allAddressesList = addressDao.getAllAddress(customerAuthEntity.getCustomer_id().toString());
            return allAddressesList;
        }
    }

    public AddressEntity deleteAddress(String address_uuid,String accessToken)throws AuthorizationFailedException,AddressNotFoundException
    {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(accessToken);
        if(customerAuthEntity==null)
        {
            throw new AuthorizationFailedException("ATHR-001","Customer is not logged in!");
        }

        final ZonedDateTime now = ZonedDateTime.now();
        if(customerAuthEntity.getLogoutAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATHR-002","Customer is logged out ! Login again to access this endpoint!");

        }
        if(customerAuthEntity.getExpiresAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATHR-003","Your session is expired.Log in again to access this endpoint!");
        }

        CustomerAddressEntity customerAddress = addressDao.matchAddressId(address_uuid);
        if(customerAuthEntity.getCustomer_id().equals(customerAddress.getCustomer_id())==false){
           throw new AuthorizationFailedException("ATHR-004","You are not authorized to view/update/delete any one else's address");
        }
        if(address_uuid==null)
        {
            throw new AddressNotFoundException("ANF-005","Address can't be empty!!");
        }

        return addressDao.deleteAddress(address_uuid);


    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List showStateList()
    {
        return addressDao.getAllStates();
    }

    public StateEntity getStateByUUID(String stateUUID) throws AddressNotFoundException {
        StateEntity stateEntity = stateDao.getStateByUUID(stateUUID);
        if (stateEntity == null) {
            throw new AddressNotFoundException("ANF-002", "No state by this state id");
        }
        return stateEntity;
    }

    public AddressEntity getAddressByUUID(String address_uuid, CustomerEntity customerEntity)throws AddressNotFoundException,AuthorizationFailedException
    {
        AddressEntity addressEntity = addressDao.getAddressByUUID(address_uuid);
        if(addressEntity.equals(""))
        {
            throw new AddressNotFoundException("ANF-003","No address by this id");
        }
        else {
            CustomerAddressEntity customerAddressEntity = addressDao.matchCustomer(addressEntity);
            if (customerAddressEntity.getCustomer_id().equals(customerEntity.getId()))
            { return addressEntity;}
            else {
                throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
            }
        }
    }

}
