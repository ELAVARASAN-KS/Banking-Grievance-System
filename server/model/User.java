package com.banking.grievance.model;

import java.sql.Date;

public class User {

    private Integer userId;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String password;
    private String accountId;
    private Date registeredOn;

    public User() {}

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }
    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }
}
