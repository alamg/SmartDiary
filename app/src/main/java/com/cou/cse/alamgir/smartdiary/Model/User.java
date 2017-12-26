package com.cou.cse.alamgir.smartdiary.Model;

/**
 * Created by Alamgir on 5/16/2017.
 */

public class User {
    private String UserName;
    private String UserEmail;
    private String UserPhone;

    public User() {
    }

    public User(String userName, String userEmail, String userPhone) {
        UserName = userName;
        UserEmail = userEmail;
        UserPhone = userPhone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }
}
