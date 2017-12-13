package com.example.aderbal.bookshareapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    public void handleSubmitClick() {
        EditText defaultTxt = (EditText) findViewById(R.id.txtFirstName);
        String firstName = defaultTxt.getText().toString();
        defaultTxt = (EditText) findViewById(R.id.txtLastName);
        String lastName = defaultTxt.getText().toString();
        defaultTxt = (EditText) findViewById(R.id.txtEmail);
        String email = defaultTxt.getText().toString();
        defaultTxt = (EditText) findViewById(R.id.txtPhone);
        String phone = defaultTxt.getText().toString();
        defaultTxt = (EditText) findViewById(R.id.txtAddress);
        String address = defaultTxt.getText().toString();
        User user = new User(firstName, lastName, email, phone, address);
        Toast.makeText(this, user.firstName, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Button submit = (Button) findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmitClick();
            }
        });
    }

    private class User {
        String firstName;
        String lastName;
        String email;
        String phone;
        String address;

        private User(String firstName, String lastName, String email, String phone, String address) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}