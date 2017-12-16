package com.example.aderbal.bookshareapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.List;

public class BookAdaptor extends BaseAdapter {

    Context context;
    List<Book> books;

    //A constructor to set up our instance variables
    public BookAdaptor(Context c, List<Book> b) {
        context = c;
        books = b;
    }

    /*
    getCount is called by the framework when it needs to know how many list items there are in the list!
     */
    @Override
    public int getCount() {
        return books.size();
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
        checkedTextView.setText(books.get(position).title);

        //Respond to clicks on our listItems
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBookClick(books.get(position));
            }

            public void onBookClick (Book book) {
                Intent editBookIntent = new Intent(context,ManageBooks.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
                editBookIntent.putExtra("action","update");
                editBookIntent.putExtra("bookKey",book.key);
                context.startActivity(editBookIntent);
            }
        });

        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onBookLongClick(books.get(position));
                return false;
            }

            public void onBookLongClick (Book book) {
                Intent deleteBookIntent = new Intent(context,ManageBooks.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
                deleteBookIntent.putExtra("action","delete");
                deleteBookIntent.putExtra("bookKey",book.key);
                context.startActivity(deleteBookIntent);
            }
        });
        return row;
    }
}
