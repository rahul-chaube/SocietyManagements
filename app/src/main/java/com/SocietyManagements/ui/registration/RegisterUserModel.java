package com.SocietyManagements.ui.registration;

public class RegisterUserModel {
    private String email,uid,userName,mobileNumber,password;
    private  long createDate;
    int my_balance;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate() {
        this.createDate = System.currentTimeMillis();
    }

    public RegisterUserModel() {
    }

    public RegisterUserModel(String email, String uid, String userName, String mobileNumber, String password) {
        this.email = email;
        this.uid = uid;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.createDate=System.currentTimeMillis();
        this.my_balance=100;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
