package com.example.aderbal.bookshareapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FriendFinishLend extends AppCompatActivity {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private String bookKey;
    private String bookTitle;
    private String friendName;
    private String friendKey;
    private String username;

    public void handleBtnConfirmLendClick() {
        DatabaseReference ref = mDatabase.getReference().child(username).child("books").child(bookKey).child("borrowed");
        ref.setValue(true);
        ref = mDatabase.getReference().child(username).child("books").child(bookKey).child("borrowedTo");
        TextView txtFriendLendFinishName = (TextView) findViewById(R.id.txtFriendLendFinishName);
        ref.setValue(txtFriendLendFinishName.getText().toString());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_finish_lend);
        username = getString(R.string.username);

        Bundle extras = getIntent().getExtras();
        bookKey = extras.getString("bookKey");
        bookTitle = extras.getString("bookTitle");
        friendName = extras.getString("friendName");
        friendKey = extras.getString("friendKey");

        TextView txtBookLendFinishTitle = (TextView) findViewById(R.id.txtBookLendFinishTitle);
        txtBookLendFinishTitle.setText(bookTitle);
        txtBookLendFinishTitle.setKeyListener(null);
        TextView txtFriendLendFinishName = (TextView) findViewById(R.id.txtFriendLendFinishName);
        if(friendKey == null)
            txtFriendLendFinishName.setText(friendName);
        else
            txtFriendLendFinishName.setText(friendKey+"@gmail.com");
        txtFriendLendFinishName.setKeyListener(null);
        Button btnConfirmLend = (Button) findViewById(R.id.btnConfirmLend);
        btnConfirmLend.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnConfirmLendClick();
                    }
                }
        );
    }
}
