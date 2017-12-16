package com.example.aderbal.bookshareapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewLoanRequestFriend extends AppCompatActivity {
    private String username;
    private String bookKey;
    private String bookTitle;
    private List<Friend> friends = new ArrayList<>();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();


    public void handleBtnFriendLendNameClick() {
        TextView txtFriendLendName = (TextView) findViewById(R.id.txtFriendLendName);
        if(txtFriendLendName.getText().toString().equals("")){
            Toast.makeText(this, "You have to input your friend's name!", Toast.LENGTH_LONG).show();
        } else {
            String friendName = txtFriendLendName.getText().toString();
            Intent newLendBookIntent = new Intent();
            newLendBookIntent.setComponent(new ComponentName("com.example.aderbal.bookshareapp", "com.example.aderbal.bookshareapp.FriendFinishLend"));
            newLendBookIntent.putExtra("bookKey", bookKey);
            newLendBookIntent.putExtra("bookTitle", bookTitle);
            newLendBookIntent.putExtra("friendName", friendName);
            setResult(Activity.RESULT_OK, newLendBookIntent);
            startActivity(newLendBookIntent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_loan_request_friend);
        username = getString(R.string.username);
        Bundle extras = getIntent().getExtras();
        bookKey = extras.getString("bookKey");
        bookTitle = extras.getString("bookTitle");

        new LoadFriends().execute("");

        Button btnFriendLendName = (Button) findViewById(R.id.btnFriendLendName);

        btnFriendLendName.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnFriendLendNameClick();
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
                        if(newFriend.status.equals("accepted"))
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
                if(friends.size() != 0) {
                    ListView listView = (ListView) findViewById(R.id.friendNameToLend);
                    FriendLendAdaptor friendLendAdaptor = new FriendLendAdaptor(getBaseContext(), friends, bookKey, bookTitle);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(friendLendAdaptor);
                }
                //Once finished all we do here is print out the headlines...
                //Or extends this to refresh any views you have (if necessary)
                Log.d("ASYNCTASK COMPLETE", "Components loaded!");
            }
        }
    }
}
