package com.example.aderbal.bookshareapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.List;

public class FriendAdaptor extends BaseAdapter {
    Context context;
    List<Friend> friends;

    //A constructor to set up our instance variables
    public FriendAdaptor(Context c, List<Friend> f) {
        context = c;
        friends = f;
    }

    /*
    getCount is called by the framework when it needs to know how many list items there are in the list!
     */
    @Override
    public int getCount() {
        return friends.size();
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
        row = inflater.inflate(R.layout.custom_list_book, null); //The second argument is the root view (in this case, none)
        //We need to set up the child views that will go into our custom_list layout
        //Get a reference to the TextView and set its text
        CheckedTextView checkedTextView = (CheckedTextView) row.findViewById(R.id.checkedTextView);
        checkedTextView.setText(friends.get(position).friendName);

        //Respond to clicks on our listItems
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onFriendClick(books.get(position));

                //TODO: Fazer algo para pegar o hash (e-mail) certo pro edit
            }

            public void onFriendClick(Friend friend) {

            }
        });
        return row;
    }
}
