package com.example.sharingapp;

public class ReadWriteUserDetails {
    public String  dob, gender;


    public ReadWriteUserDetails() {};

    public ReadWriteUserDetails(String textDoB, String textGender) {
        this.dob = textDoB;
        this.gender = textGender;
    }
}
