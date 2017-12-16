package com.example.aderbal.bookshareapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewLoanRequest extends AppCompatActivity {
    List<Book> books = new ArrayList<>();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_loan_request);

        username = getString(R.string.username);

        new LoadBooks().execute("");
    }

    private class LoadBooks extends AsyncTask<String, Integer, Integer> {
        int success = 0;
        @Override
        protected Integer doInBackground(String... strings) {
            try {
                DatabaseReference ref = mDatabase.getReference().child(username).child("books");

                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        Book newBook = dataSnapshot.getValue(Book.class);
                        if(!newBook.borrowed) {
                            newBook.key = dataSnapshot.getKey();
                            books.add(newBook);
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
                ListView listView = (ListView) findViewById(R.id.newLoanBook);
                BookNewLoanAdaptor bookNewLoanAdaptor = new BookNewLoanAdaptor(getBaseContext(), books);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(bookNewLoanAdaptor);
                //Once finished all we do here is print out the headlines...
                //Or extends this to refresh any views you have (if necessary)
                Log.d("ASYNCTASK COMPLETE", "Components loaded!");
            }
        }
    }
}
