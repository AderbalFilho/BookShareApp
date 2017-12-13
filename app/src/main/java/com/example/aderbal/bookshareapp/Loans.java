package com.example.aderbal.bookshareapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Loans extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans);

        ListView listView = (ListView) findViewById(R.id.manageLoans);
        List<String> borrows = new ArrayList<String>();
        borrows.add("Book 1 borrowed to Friend 1");
        borrows.add("Book 2 borrowed to Friend 1");
        borrows.add("Book 3 borrowed to Friend 2");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                borrows
        );
        listView.setAdapter(arrayAdapter);
    }
}
