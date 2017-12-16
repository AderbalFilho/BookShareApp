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

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private String friendKey;
    private String friendName;
    private String action;
    private String username;

    public void handleAddNewFriendclick() {
        if(action.equals("add")) {
            TextView txtNewFriendEmail = (TextView) findViewById(R.id.txtNewFriendEmail);
            String email = txtNewFriendEmail.getText().toString();
            TextView txtNewFriendName = (TextView) findViewById(R.id.txtNewFriendName);
            String name = txtNewFriendName.getText().toString();
            if (!email.equals("") && !name.equals("")) {
                mDatabase = FirebaseDatabase.getInstance();
                friendKey = email.substring(0, email.indexOf("@"));
                DatabaseReference ref = mDatabase.getReference().child(username).child("friends").child(friendKey);
                ref.setValue(new Friend(email, name));
                ref = mDatabase.getReference().child(friendKey).child("friends").child(username);
                Friend i = new Friend(username + "@gmail.com", username);
                i.invite = "received";
                ref.setValue(i);
                Toast.makeText(this, "Friend invited!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Empty field(s)! Try again.", Toast.LENGTH_LONG).show();
                finish();
            }
        } else if (action.equals("delete")) {
            DatabaseReference ref = mDatabase.getReference().child(username).child("friends").child(friendKey);
            ref.setValue(null);
            ref = mDatabase.getReference().child(friendKey).child("friends").child(username);
            ref.setValue(null);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        username = getString(R.string.username);

        Bundle extras = getIntent().getExtras();
        action = extras.getString("action");
        Button btnAddNewFriend = (Button) findViewById(R.id.btnAddNewFriend);

        if(action.equals("delete")){
            friendKey = extras.getString("friendKey");
            friendName = extras.getString("friendName");
            TextView txtNewFriendName = (TextView) findViewById(R.id.txtNewFriendName);
            txtNewFriendName.setText(friendName);
            TextView txtNewFriendEmail = (TextView) findViewById(R.id.txtNewFriendEmail);
            txtNewFriendEmail.setText(friendKey+"@gmail.com");
            btnAddNewFriend.setText("Delete friend?");
        }

        btnAddNewFriend.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleAddNewFriendclick();
                    }
                }
        );
    }

}
