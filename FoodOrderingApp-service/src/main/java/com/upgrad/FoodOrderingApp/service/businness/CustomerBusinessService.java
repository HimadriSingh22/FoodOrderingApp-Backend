package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class CustomerBusinessService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    //Signup method
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity signup(CustomerEntity customerEntity)throws SignUpRestrictedException{

        //Checking if any field is empty other than lastname
         if(customerEntity.getFirstname()==null||customerEntity.getContact_number()==null||customerEntity.getEmail()==null||customerEntity.getPassword()==null)
         {
             throw new SignUpRestrictedException("SGR-005","All fields except lastName should be filled!!");
         }
        // Checking password strength
         boolean strong =checkPasswordStrength(customerEntity.getPassword());
         if(strong==false)
         {
             throw new SignUpRestrictedException("SGR-004","Weak Password!!");
         }

        // checking whether the number already exists in the database
        CustomerEntity customerEntity1=customerDao.getCustomerByContact(customerEntity.getContact_number());
        if(customerEntity1!=null ){
            throw new SignUpRestrictedException("SGR-001","This contact number is already registered!! Try other contact number!!");
        }

        // checking whether email is valid or not
        boolean isValid = validEmail(customerEntity.getEmail());
        if(isValid==false)
        {
            throw new SignUpRestrictedException("SGR-002","Invalid Email Format!!");
        }

        //checking whether contact is valid or not
        boolean isFormat = validContact(customerEntity.getContact_number());
        if(isFormat==false)
        {
            throw new SignUpRestrictedException("SGR-003","Invalid Contact Number");
        }

        else {
            String[] encryptedText = cryptographyProvider.encrypt(customerEntity.getPassword());
            customerEntity.setSalt(encryptedText[0]);
            customerEntity.setPassword(encryptedText[1]);
            return customerDao.createCustomer(customerEntity);
        }
    }

    //method to check email format

    public boolean validEmail(String email)
    {

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"; ;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    //method to check contact number format
    public boolean validContact(String contact){
        boolean p=false;
        if(contact.length()==10){
            Pattern pattern = Pattern.compile("[0-9]");
            p = pattern.matcher(contact).find();
        }
        return p;
    }

    // method to check password strength
    public boolean checkPasswordStrength(String password)
    {
        boolean strong=false;
        if(password.length()>=8)
        {
            Pattern capital = Pattern.compile("[A-Z]");
            Pattern small =Pattern.compile("[a-z]");
            Pattern digit=Pattern.compile("[0-9]");
            Pattern special=Pattern.compile("[!@#$%^&*()_+=/?>.<'|]");
            Matcher hasUpperCase=capital.matcher(password);
            Matcher hasLowerCase=small.matcher(password);
            Matcher hasDigit =digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);
            if(hasUpperCase.find()&&hasLowerCase.find()&&hasDigit.find()&&hasSpecial.find())
            {
                strong=true;
            }
        }
        return strong;
    }



    //login method
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(final String username , final String password) throws AuthenticationFailedException{
        CustomerEntity customerEntity = customerDao.getCustomerByContact(username);
        if(customerEntity==null)
        {
            throw new AuthenticationFailedException("ATH-001","This  contact number has not been registered");
        }
         final String encryptedPassword =cryptographyProvider.encrypt(password,customerEntity.getSalt());
        if(encryptedPassword.equals(customerEntity.getPassword()))
        {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
            customerAuthEntity.setCustomer_id(customerEntity);

            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt=now.plusHours(8);
            customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(),now,expiresAt));
            customerAuthEntity.setLoginAt(now);
            customerAuthEntity.setExpiresAt(expiresAt);

            customerDao.createAuthToken(customerAuthEntity);
            customerDao.updateCustomer(customerEntity);
            return customerAuthEntity;
        }
        else{
            throw new AuthenticationFailedException("ATH-002","Invalid Credentials");
        }
    }

    //logout method
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity logout(String accessToken)throws AuthorizationFailedException
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
        else
            {
        customerAuthEntity.setLogoutAt(now);
        customerDao.updateCustomerAuthEntity(customerAuthEntity);
        return customerAuthEntity;
        }
    }

    // update customer details method
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomer(String accessToken,CustomerEntity updateCustomer) throws AuthorizationFailedException,UpdateCustomerException {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(accessToken);
        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATH-001", "Customer is not logged in!");
        }

        final ZonedDateTime now = ZonedDateTime.now();
        if (customerAuthEntity.getLogoutAt().isBefore(now)) {
            throw new AuthorizationFailedException("ATH-002", "Customer is logged out ! Login again to access this endpoint!");

        }
        if (customerAuthEntity.getExpiresAt().isBefore(now)) {
            throw new AuthorizationFailedException("ATH-003", "Your session is expired.Log in again to access this endpoint!");
        }
        else {
            CustomerEntity updatedCustomer = customerDao.updateCustomerDetails(updateCustomer);
            return updatedCustomer;
        }
    }

    //update password method
    @Transactional(propagation = Propagation.REQUIRED)
        public CustomerEntity updatePassword(String accessToken , CustomerEntity updateOldPassword ,String oldPassword )throws  AuthorizationFailedException,UpdateCustomerException
        {
            //matching old password
            final String encryptedPassword = cryptographyProvider.encrypt(oldPassword,updateOldPassword.getSalt());
            if(encryptedPassword.equals(oldPassword)==false)
            {
                throw new UpdateCustomerException("UCR-004","Incorrect Old Password!");
            }
            // Checking password strength
            boolean strong =checkPasswordStrength(updateOldPassword.getPassword());
            if(strong==false)
            {
                throw new UpdateCustomerException("UCR-001","Weak Password!!");
            }

            //authenticating customer
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

            else
            {
                String[] encryptedText = cryptographyProvider.encrypt(updateOldPassword.getPassword());
                updateOldPassword.setSalt(encryptedText[0]);
                updateOldPassword.setPassword(encryptedText[1]);
                return customerDao.updatePassword(updateOldPassword);
            }
        }
    }





