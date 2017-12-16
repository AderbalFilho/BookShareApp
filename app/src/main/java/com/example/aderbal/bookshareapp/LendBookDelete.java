package com.example.aderbal.bookshareapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LendBookDelete extends AppCompatActivity {
    private String bookKey;
    private String action;
    private String bookTitle;
    private String bookBorrowedTo;
    private String username;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    public void handleBtnDeleteBookLendedClick() {
        DatabaseReference ref = mDatabase.getReference().child(username).child("books").child(bookKey).child("borrowed");
        ref.setValue(false);
        ref = mDatabase.getReference().child(username).child("books").child(bookKey).child("borrowedTo");
        ref.setValue(null);
        Toast.makeText(this, "Lend deleted!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_book_delete);

        username = getString(R.string.username);
        Bundle extras = getIntent().getExtras();
        action = extras.getString("action");
        if(action.equals("deleteLend")) {
            bookKey = extras.getString("bookKey");
            bookTitle = extras.getString("bookTitle");
            bookBorrowedTo = extras.getString("bookBorrowedTo");

            TextView txtBookLendedTitle = (TextView) findViewById(R.id.txtBookLendedTitle);
            txtBookLendedTitle.setText(bookTitle);
            TextView txtFriendLendedEmail = (TextView) findViewById(R.id.txtFriendLendedEmail);
            txtFriendLendedEmail.setText(bookBorrowedTo);
            Button btnDeleteBookLended = (Button) findViewById(R.id.btnDeleteBookLended);
            btnDeleteBookLended.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            handleBtnDeleteBookLendedClick();
                        }
                    }
            );
        }
    }
}
