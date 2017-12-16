package com.example.aderbal.bookshareapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PendingFriendAdaptor extends BaseAdapter {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private Context context;
    private List<Friend> pendingFriends;
    private String username;

    //A constructor to set up our instance variables
    public PendingFriendAdaptor(Context c, List<Friend> pf, String u) {
        context = c;
        pendingFriends = pf;
        username = u;
    }

    /*
    getCount is called by the framework when it needs to know how many list items there are in the list!
     */
    @Override
    public int getCount() {
        return pendingFriends.size();
    }

    /*
    This method is required by the framework, however we don't need it for anything, so we leave a stub implementation
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /*
    This method is supposed to get the id of an item at position
    Not needed, so leaving the stub implementation
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /*
    The android framework will call getView every time it needs to draw your custom list item
    The int position indicates which row of your listView the framework is trying to render
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Create a new View
        View row = null; //A reference that will refer to the current row being rendered
        /*
        We're going to use the LayoutInflater class to instantiate a Java object from the xml layout
        First we need to get an instance of LayoutInflater throught the getSystemService method
        Recall that context is our reference to MainActivity which is itself and Android Context
         */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Use the LayoutInflater instance to instantiate a new View of the "correct type"
        row = inflater.inflate(R.layout.pending_friend, null); //The second argument is the root view (in this case, none)
        //We need to set up the child views that will go into our custom_list layout
        //Get a reference to the TextView and set its text
        TextView lbFriendInviteName = (TextView) row.findViewById(R.id.lbFriendInviteName);
        lbFriendInviteName.setText(pendingFriends.get(position).friendName);

        final LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.listViewFriendInvite);

        Button btnAcceptFriend = (Button) row.findViewById(R.id.btnAcceptFriend);
        btnAcceptFriend.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnAcceptFriendClick(position, linearLayout);
                    }
                }
        );
        Button btnDeclineFriend = (Button) row.findViewById(R.id.btnDeclineFriend);
        btnDeclineFriend.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnDeclineFriendClick(position, linearLayout);
                    }
                }
        );
        return row;
    }

    public void handleBtnAcceptFriendClick(int position, LinearLayout linearLayout) {
        String friendKey = pendingFriends.get(position).friendEmail.substring(0,pendingFriends.get(position).friendEmail.indexOf("@"));
        DatabaseReference ref = mDatabase.getReference().child(username).child("friends").child(friendKey).child("status");
        ref.setValue("accepted");
        ref = mDatabase.getReference().child(friendKey).child("friends").child(username).child("status");
        ref.setValue("accepted");
        linearLayout.setVisibility(View.INVISIBLE);
    }

    public void handleBtnDeclineFriendClick(int position, LinearLayout linearLayout) {
        String friendKey = pendingFriends.get(position).friendEmail.substring(0,pendingFriends.get(position).friendEmail.indexOf("@"));
        DatabaseReference ref = mDatabase.getReference().child(username).child("friends").child(friendKey);
        ref.setValue(null);
        ref = mDatabase.getReference().child(friendKey).child("friends").child(username);
        ref.setValue(null);
        linearLayout.setVisibility(View.INVISIBLE);
    }
}
