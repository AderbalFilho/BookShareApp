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
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCollection extends AppCompatActivity {
    List<Book> books = new ArrayList<>();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private String username;

    public void handleBtnAddBookclick() {
        Intent addBookIntent = new Intent();
        addBookIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.ManageBooks"));
        addBookIntent.putExtra("action","create");
        setResult(Activity.RESULT_OK, addBookIntent);
        startActivity(addBookIntent);
    }

    public void handleBtnHelpBookclick() {
        Toast.makeText(this, "Click to update, long click to delete", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_collection);
        username = getString(R.string.username);

        new LoadBooks().execute("");

        Button btnAddBook = (Button) findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnAddBookclick();
                    }
                }
        );

        Button btnHelpBook = (Button) findViewById(R.id.btnHelpBook);
        btnHelpBook.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnHelpBookclick();
                    }
                }
        );
    }

    private class LoadBooks extends AsyncTask<String, Integer, Integer> {
        int success = 0;
        @Override
        protected Integer doInBackground(String... strings) {
            try {
                DatabaseReference ref = mDatabase.getReference().child(username).child("books");
                /*
                //Pegar um único valor
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Book book = dataSnapshot.getValue(Book.class);
                        System.out.println(book);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });*/

                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        Book newBook = dataSnapshot.getValue(Book.class);
                        newBook.key = dataSnapshot.getKey();
                        if(newBook.borrowed)
                            newBook.title = newBook.title + " (borrowed)";
                        books.add(newBook);
                        onProgressUpdate();
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
                ListView listView = (ListView) findViewById(R.id.manageBookCollection);

                BookAdaptor bookAdaptor = new BookAdaptor(getBaseContext(), books);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(bookAdaptor);
                //Once finished all we do here is print out the headlines...
                //Or extends this to refresh any views you have (if necessary)
                Log.d("ASYNCTASK COMPLETE", "Components loaded!");
            }
        }
    }
}
