package com.example.aderbal.bookshareapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Friend {
    public String friendEmail;
    public String friendName;
    public String invite = "sent";
    public String status = "pending";

    public Friend() {

    }

    public Friend(String friendEmail, String friendName) {
        this.friendEmail = friendEmail;
        this.friendName = friendName;
    }
}
