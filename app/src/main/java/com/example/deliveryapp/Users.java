package com.example.deliveryapp;

public class Users {
    public Users() {
    }

    public Users(String cusId, String phone, String fullname, String email, String password) {
        this.cusId = cusId;
        this.phone = phone;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    private String cusId;
    private String phone;
    private String fullname;
    private String email;
    private String password;


    public String getcusId() {
        return cusId;
    }

    public void setcusId(String cusId) {
        this.cusId = cusId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
