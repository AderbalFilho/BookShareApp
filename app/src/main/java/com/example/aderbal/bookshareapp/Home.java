package com.example.aderbal.bookshareapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    public void handleBtnFriendsclick() {
        Intent friendsIntent = new Intent();
        friendsIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.ManageFriends"));
        setResult(Activity.RESULT_OK, friendsIntent);
        startActivity(friendsIntent);
    }

    public void handleBtnMyLibraryclick() {
        Intent myLibraryIntent = new Intent();
        myLibraryIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.ManageCollection"));
        setResult(Activity.RESULT_OK, myLibraryIntent);
        startActivity(myLibraryIntent);
    }

    public void handleBtnLoansclick() {
        Intent loansIntent = new Intent();
        loansIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.Loans"));
        setResult(Activity.RESULT_OK, loansIntent);
        startActivity(loansIntent);
    }

    public void handleBtnRemindersclick() {
        Intent remindersIntent = new Intent();
        remindersIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.Reminders"));
        setResult(Activity.RESULT_OK, remindersIntent);
        startActivity(remindersIntent);
    }

    public void handleBtnPublicLibraryclick() {
        Intent publicLibraryIntent = new Intent();
        publicLibraryIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.PublicLibrary"));
        setResult(Activity.RESULT_OK, publicLibraryIntent);
        startActivity(publicLibraryIntent);
    }

    public void handleBtnSignOutclick() {
        this.finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnFriends = (Button) findViewById(R.id.btnFriends);
        btnFriends.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleBtnFriendsclick();
                }
            }
        );

        Button btnMyLibrary = (Button) findViewById(R.id.btnMyLibrary);
        btnMyLibrary.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleBtnMyLibraryclick();
                }
            }
        );

        Button btnLoans = (Button) findViewById(R.id.btnLoans);
        btnLoans.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleBtnLoansclick();
                }
            }
        );

        Button btnReminders = (Button) findViewById(R.id.btnReminders);
        btnReminders.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleBtnRemindersclick();
                }
            }
        );

        Button btnPublicLibrary = (Button) findViewById(R.id.btnPublicLibrary);
        btnPublicLibrary.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleBtnPublicLibraryclick();
                }
            }
        );

        Button btnSignOut = (Button) findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnSignOutclick();
                    }
                }
        );
    }
}
