package com.example.aderbal.bookshareapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Loans extends AppCompatActivity {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    List<Book> booksRequested = new ArrayList<>();
    List<Book> booksLended = new ArrayList<>();
    private String username;

    public void handleBtnHelpLoanClick() {
        Toast.makeText(this, "Long click to delete", Toast.LENGTH_LONG).show();
    }

    public void handleBtnAddNewLoanClick() {
        Intent newLoanRequest = new Intent();
        newLoanRequest.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.NewLoanRequest"));
        setResult(Activity.RESULT_OK, newLoanRequest);
        startActivity(newLoanRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans);

        username = getString(R.string.username);
        new LoadLoanBooks().execute("");

        Button btnAddNewLoan = (Button) findViewById(R.id.btnAddNewLoan);
        btnAddNewLoan.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnAddNewLoanClick();
                    }
                }
        );

        Button btnHelpLoan = (Button) findViewById(R.id.btnHelpLoan);
        btnHelpLoan.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnHelpLoanClick();
                    }
                }
        );
    }

    private class LoadLoanBooks extends AsyncTask<String, Integer, Integer> {
        int success = 0;
        @Override
        protected Integer doInBackground(String... strings) {
            try {
                DatabaseReference ref = mDatabase.getReference().child(username).child("books");

                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        Book newBook = dataSnapshot.getValue(Book.class);
                        if(newBook.solicitations.size() > 0) {
                            newBook.key = dataSnapshot.getKey();
                            booksRequested.add(newBook);
                            onProgressUpdate();
                        }
                        if(newBook.borrowedTo != null) {
                            newBook.key = dataSnapshot.getKey();
                            booksLended.add(newBook);
                            onProgressUpdate();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                success = 1;
                return 1;
            } catch (Exception e) {
                return 0;
            }

        }

        //onPostExecute is called by the framework when the end of doInBackground is reached
        protected void onProgressUpdate() {
            if (success == 1) {
                if (booksRequested.size() > 0) {
                    ListView listView = (ListView) findViewById(R.id.manageLoansRequests);

                    BookRequestAdaptor bookRequestAdaptor = new BookRequestAdaptor(getBaseContext(), booksRequested, username);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(bookRequestAdaptor);
                }

                if (booksLended.size() > 0) {
                    ListView listView = (ListView) findViewById(R.id.manageLoans);

                    BookLendedAdaptor bookLendedAdaptor = new BookLendedAdaptor(getBaseContext(), booksLended, username);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(bookLendedAdaptor);
                }
                //Once finished all we do here is print out the headlines...
                //Or extends this to refresh any views you have (if necessary)
                Log.d("ASYNCTASK COMPLETE", "Components loaded!");
            }
        }
    }
}
