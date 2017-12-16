package com.example.aderbal.bookshareapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.List;

public class BookNewLoanAdaptor extends BaseAdapter {
    Context context;
    List<Book> newLoanbooks;

    //A constructor to set up our instance variables
    public BookNewLoanAdaptor(Context c, List<Book> b) {
        context = c;
        newLoanbooks = b;
    }

    /*
    getCount is called by the framework when it needs to know how many list items there are in the list!
     */
    @Override
    public int getCount() {
        return newLoanbooks.size();
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
        checkedTextView.setText(newLoanbooks.get(position).title);

        //Respond to clicks on our listItems
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewLoanBookClick(newLoanbooks.get(position));
            }

            public void onNewLoanBookClick (Book book) {
                Intent editBookIntent = new Intent(context,NewLoanRequestFriend.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editBookIntent.putExtra("bookKey",book.key);
                editBookIntent.putExtra("bookTitle",book.title);
                context.startActivity(editBookIntent);
            }
        });

        return row;
    }
}
