package com.example.carvefood;

public class User {
    public String hotel_name,hotel_address,user_name,email,mobile,password,user_id;

    public User(){

    }

    public User(String user_id,String hotel_name,String hotel_address,String user_name,String email,String mobile,String password){
        this.user_id = user_id;
        this.hotel_name = hotel_name;
        this.hotel_address = hotel_address;
        this.user_name = user_name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;

    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
