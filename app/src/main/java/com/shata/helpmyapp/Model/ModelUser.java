package com.shata.helpmyapp.Model;

import java.io.Serializable;

public class ModelUser implements Serializable {

    boolean userIsAdmin=false;

    String userID,
            UserPhoneNumber,
            UserName,
            UserAge,
            UserEmail,
            UserGender;

    public ModelUser() {
    }

    public ModelUser(boolean userIsAdmin, String userID, String userPhoneNumber, String userName, String userAge, String userEmail, String userGender) {
        this.userIsAdmin = userIsAdmin;
        this.userID = userID;
        UserPhoneNumber = userPhoneNumber;
        UserName = userName;
        UserAge = userAge;
        UserEmail = userEmail;
        UserGender = userGender;
    }

    public ModelUser(String userPhoneNumber, String userName, String userAge, String userEmail, String userGender) {
        UserPhoneNumber = userPhoneNumber;
        UserName = userName;
        UserAge = userAge;
        UserEmail = userEmail;
        UserGender = userGender;
    }

    public ModelUser(String userID, String userPhoneNumber, String userName, String userAge, String userEmail, String userGender) {
        this.userID = userID;
        UserPhoneNumber = userPhoneNumber;
        UserName = userName;
        UserAge = userAge;
        UserEmail = userEmail;
        UserGender = userGender;
    }

    public boolean isUserIsAdmin() {
        return userIsAdmin;
    }

    public void setUserIsAdmin(boolean userIsAdmin) {
        this.userIsAdmin = userIsAdmin;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserAge() {
        return UserAge;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserGender() {
        return UserGender;
    }

    public void setUserGender(String userGender) {
        UserGender = userGender;
    }

}

