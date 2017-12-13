package com.example.aderbal.bookshareapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class AddFriend extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    final private String username = "friend2";

    public void handleAddNewFriendclick() {
        TextView txtNewFriendEmail = (TextView) findViewById(R.id.txtNewFriendEmail);
        String email = txtNewFriendEmail.getText().toString();
        TextView txtNewFriendName = (TextView) findViewById(R.id.txtNewFriendName);
        String name = txtNewFriendName.getText().toString();
        if(!email.equals("") && !name.equals("")){
            mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference ref = mDatabase.getReference().child(username).child("friends");
            ref.push().setValue(new Friend(email, name));
            Toast.makeText(this, "Friend invited!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Empty field(s)! Try again.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        Button btnAddNewFriend = (Button) findViewById(R.id.btnAddNewFriend);
        btnAddNewFriend.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleAddNewFriendclick();
                    }
                }
        );
    }

}
