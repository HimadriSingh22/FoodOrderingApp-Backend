package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerBusinessService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    //Signup method
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity signup(CustomerEntity customerEntity)throws SignUpRestrictedException{

        //Checking if any field is empty other than lastname
         if(customerEntity.getFirstname()==null||customerEntity.getContact_no()==null||customerEntity.getEmail()==null||customerEntity.getPassword()==null)
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
        CustomerEntity customerEntity1=customerDao.getCustomerByContact(customerEntity.getContact_no());
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
        boolean isFormat = validContact(customerEntity.getContact_no());
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
            customerAuthEntity.setCustomer(customerEntity);

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


}
