package com.example.aderbal.bookshareapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookRequestAdaptor extends BaseAdapter {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private Context context;
    private List<Book> booksRequested = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private String username;

    //A constructor to set up our instance variables
    public BookRequestAdaptor(Context c, List<Book> br, String u) {
        context = c;
        books = br;
        for(Book book: br) {
            for(String solicitation: book.solicitations) {
                Book newBook = book;
                newBook.solicitations = new ArrayList<>();
                newBook.solicitations.add(solicitation);
                booksRequested.add(newBook);
            }
        }
        username = u;
    }

    /*
    getCount is called by the framework when it needs to know how many list items there are in the list!
     */
    @Override
    public int getCount() {
        return booksRequested.size();
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
        row = inflater.inflate(R.layout.book_request, null); //The second argument is the root view (in this case, none)
        //We need to set up the child views that will go into our custom_list layout
        //Get a reference to the TextView and set its text
        TextView lbRequestingFriendName = (TextView) row.findViewById(R.id.lbRequestingFriendName);
        lbRequestingFriendName.setText(booksRequested.get(position).solicitations.get(0));
        TextView lbDefaultRequestText = (TextView) row.findViewById(R.id.lbDefaultRequestText);
        lbDefaultRequestText.setText("Can you borrow me this book: " + booksRequested.get(position).title + "?");

        final LinearLayout linearLayout = (LinearLayout) row.findViewById(R.id.listViewBookRequest);

        Button btnAcceptBorrow = (Button) row.findViewById(R.id.btnAcceptBorrow);
        btnAcceptBorrow.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnAcceptBorrowClick(position, linearLayout);
                    }
                }
        );
        Button btnDeclineBorrow = (Button) row.findViewById(R.id.btnDeclineBorrow);
        btnDeclineBorrow.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        handleBtnDeclineBorrowClick(position, linearLayout);
                    }
                }
        );
        return row;
    }

    public void handleBtnAcceptBorrowClick(int position, LinearLayout linearLayout) {
        if(!booksRequested.get(position).borrowed) {
            String friendKey = booksRequested.get(position).solicitations.get(0);
            DatabaseReference ref = mDatabase.getReference().child(username).child("books").child(booksRequested.get(position).key).child("borrowed");
            ref.setValue(true);
            ref = mDatabase.getReference().child(username).child("books").child(booksRequested.get(position).key).child("borrowedTo");
            ref.setValue(friendKey + "@gmail.com");
            ref = mDatabase.getReference().child(username).child("books").child(booksRequested.get(position).key).child("solicitations");
            List<String> sol = books.get(position).solicitations;
            sol.remove(booksRequested.get(position).solicitations.get(0));
            ref.setValue(sol);
            linearLayout.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(context, "This book has been already borrowed", Toast.LENGTH_LONG).show();
        }
    }

    public void handleBtnDeclineBorrowClick(int position, LinearLayout linearLayout) {
        DatabaseReference ref = mDatabase.getReference().child(username).child("books").child(booksRequested.get(position).key).child("solicitations");
        List<String> sol = books.get(position).solicitations;
        sol.remove(booksRequested.get(position).solicitations.get(0));
        ref.setValue(sol);
        linearLayout.setVisibility(View.INVISIBLE);
    }
}
