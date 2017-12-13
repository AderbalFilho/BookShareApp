package com.example.aderbal.bookshareapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private SharedPreferences mSharedPreferences;
    private String mUsername;
    private String mPhotoUrl;

    public void handleLbForgotPasswordclick() {
        Intent tv1intent = new Intent();
        tv1intent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.ForgotPassword"));
        setResult(Activity.RESULT_OK, tv1intent);
        startActivity(tv1intent);
    }

    public void handleLbCreateAccountclick() {
        Intent createAccountIntent = new Intent();
        createAccountIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.CreateAccount"));
        setResult(Activity.RESULT_OK, createAccountIntent);
        startActivity(createAccountIntent);
    }

    public void handleBtnLoginclick() {
        Intent logInIntent = new Intent();
        logInIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.Home"));
        setResult(Activity.RESULT_OK, logInIntent);
        startActivity(logInIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = "anonymous";

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
            handleBtnLoginclick(/* private FirebaseAuth mFirebaseAuth;
                private FirebaseUser mFirebaseUser;
                private SharedPreferences mSharedPreferences;
                private String mUsername;
                private String mPhotoUrl; *//*);
            finish();
        }*/

        TextView lbForgotPassword = (TextView) findViewById(R.id.lbForgotPassword);
        lbForgotPassword.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleLbForgotPasswordclick();
                }
            }
        );

        TextView lbCreateAccount = (TextView) findViewById(R.id.lbCreateAccount);
        lbCreateAccount.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleLbCreateAccountclick();
                }
            }
        );

        Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleBtnLoginclick();
                }
            }
        );
    }
}
