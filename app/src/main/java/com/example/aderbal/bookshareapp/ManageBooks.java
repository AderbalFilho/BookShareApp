package com.example.aderbal.bookshareapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ManageBooks extends AppCompatActivity {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private Book book;
    private String bookKey;
    private String action;
    final private String username = "friend2";

    public void handleBtnSaveclick (int bookId) {
        EditText etTitle = (EditText) findViewById(R.id.txtTitle);
        String title = etTitle.getText().toString();
        if (!title.equals("")) {
            EditText txtAuthor = (EditText) findViewById(R.id.txtAuthor);
            String author = txtAuthor.getText().toString();
            EditText txtPublisher = (EditText) findViewById(R.id.txtPublisher);
            String publisher = txtPublisher.getText().toString();
            EditText txtYear = (EditText) findViewById(R.id.txtYear);
            int year;
            try{
                year = Integer.parseInt(txtYear.getText().toString());
            } catch (Exception e) {
                year = -1;
            }
            EditText txtNoPages = (EditText) findViewById(R.id.txtNoPages);
            int nPages;
            try{
                nPages = Integer.parseInt(txtNoPages.getText().toString());
            } catch (Exception e) {
                nPages = -1;
            }
            EditText txtCategory = (EditText) findViewById(R.id.txtCategory);
            String category = txtCategory.getText().toString();
            Book book = new Book(title, author, publisher, year, nPages, category);
            if (action.equals("create")) {
                //book.bookId = Book.bookIdCounter;
                /*mDatabase.child(username).child("books").child(String.valueOf(Book.bookIdCounter)).setValue(book);
                Book.bookIdCounter++;*/
                DatabaseReference ref = mDatabase.getReference().child(username).child("books");
                ref.push().setValue(book);
                Toast.makeText(this, "Book successfully added!", Toast.LENGTH_LONG).show();
            } else if (action.equals("update")) {
                DatabaseReference ref = mDatabase.getReference().child(username).child("books").child(bookKey);
                ref.setValue(book);
                Toast.makeText(this, "Book successfully updated!", Toast.LENGTH_LONG).show();
            } else if (action.equals("delete")) {
                //TODO: Adicionar função para chamar isso.
                DatabaseReference ref = mDatabase.getReference().child(username).child("books").child(bookKey);
                ref.setValue(null);
                Toast.makeText(this, "Book deleted!", Toast.LENGTH_LONG).show();
            }
            finish();
        } else {
            Toast.makeText(this,"You need to enter at least the title!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);


        Button btnSaveNewBook = (Button) findViewById(R.id.btnSaveNewBook);
        final Bundle extras = getIntent().getExtras();
        action = extras.getString("action");
        if (action.equals("update")) {
            bookKey = extras.getString("bookKey");
            new LoadEditBook().execute("");
        }

        btnSaveNewBook.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        int bookId = -1;
                        if (action.equals("update")) {
                            bookId = extras.getInt("bookId");
                        }
                        handleBtnSaveclick(bookId);
                    }
                }
        );
    }

    private void loadData() {
        if(book != null) {
            Button btnSaveNewBook = (Button) findViewById(R.id.btnSaveNewBook);
            EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
            txtTitle.setText(book.title);
            EditText txtAuthor = (EditText) findViewById(R.id.txtAuthor);
            txtAuthor.setText(book.author);
            EditText txtPublisher = (EditText) findViewById(R.id.txtPublisher);
            txtPublisher.setText(book.publisher);
            EditText txtYear = (EditText) findViewById(R.id.txtYear);
            if (book.year == -1)
                txtYear.setText("");
            else
                txtYear.setText(String.valueOf(book.year));
            EditText txtNoPages = (EditText) findViewById(R.id.txtNoPages);
            if (book.nPages == -1)
                txtNoPages.setText("");
            else
                txtNoPages.setText(String.valueOf(book.nPages));
            EditText txtCategory = (EditText) findViewById(R.id.txtCategory);
            txtCategory.setText(book.category);
            TextView textView = (TextView) findViewById(R.id.lbAddUpdateBook);
            textView.setText("Update book");
            btnSaveNewBook.setText("Update");
        } else {
            Toast.makeText(getBaseContext(), "Error during load!", Toast.LENGTH_LONG).show();
        }
    }

    protected class LoadEditBook extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            DatabaseReference ref = mDatabase.getReference().child(username).child("books").child(bookKey);
            //Pegar um único valor
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    book = dataSnapshot.getValue(Book.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
            return 1;
        }

        //onPostExecute is called by the framework when the end of doInBackground is reached
        protected void onPostExecute(Integer result) {
            //Once finished all we do here is print out the headlines...
            //Or extends this to refresh any views you have (if necessary)
            Log.d("ASYNCTASK COMPLETE", "Read " + result + " row(s)");
            loadData();
        }
    }
}
