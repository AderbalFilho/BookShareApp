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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManageFriends extends AppCompatActivity {

    final private String username = "friend2";

    List<Friend> friends = new ArrayList<>();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    public void handleBtnFriendLibraryclick() {
        Intent friendLibraryIntent = new Intent();
        friendLibraryIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.FriendCollection"));
        setResult(Activity.RESULT_OK, friendLibraryIntent);
        startActivity(friendLibraryIntent);
    }

    public void handleAddFriendclick() {
        Intent addFriendIntent = new Intent();
        addFriendIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.AddFriend"));
        setResult(Activity.RESULT_OK, addFriendIntent);
        startActivity(addFriendIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friends);

        new LoadFriends().execute("");

        /*
        ListView listView = (ListView) findViewById(R.id.manageFriends);
        List<String> friends = new ArrayList<>();
        friends.add("Friend 1");
        friends.add("Friend 2");
        friends.add("Friend 3");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                friends
        );
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(arrayAdapter); */

        //TODO: Ajeitar para quando o usuário clicar no item da lista
        Button btnSeeLibrary = (Button) findViewById(R.id.btnFriendLibrary);
        btnSeeLibrary.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v) {
                    handleBtnFriendLibraryclick();
                }
            }
        );

        Button btnAddFriend = (Button) findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleAddFriendclick();
                    }
                }
        );
    }

    private class LoadFriends extends AsyncTask<String, Integer, Integer> {
        int success = 0;

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                DatabaseReference ref = mDatabase.getReference().child(username).child("friends");
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        Friend newFriend = dataSnapshot.getValue(Friend.class);
                        friends.add(newFriend);
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
                ListView listView = (ListView) findViewById(R.id.manageFriends);

                FriendAdaptor friendAdaptor = new FriendAdaptor(getBaseContext(), friends);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(friendAdaptor);
                //Once finished all we do here is print out the headlines...
                //Or extends this to refresh any views you have (if necessary)
                Log.d("ASYNCTASK COMPLETE", "Components loaded!");
            }
        }
    }
}
