package com.example.aderbal.bookshareapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Book {
    public String title;
    public String author;
    public String publisher;
    public int year;
    public int nPages;
    public String category;
    public String key;
    public static int bookIdCounter = 1;
    public int bookId;

    public Book() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, String author, String publisher, int year, int nPages, String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.nPages = nPages;
        this.category = category;
    }

    public Book(String title, String author, String publisher, int year, int nPages, String category, int bookId) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.nPages = nPages;
        this.category = category;
        this.bookId = bookId;
    }

    public Book(String title, String author, String publisher, int year, int nPages, String category, String bookKey) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.nPages = nPages;
        this.category = category;
        this.key = bookKey;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("author", author);
        result.put("publisher", publisher);
        result.put("year", year);
        result.put("nPages", nPages);
        result.put("this.category", category);

        return result;
    }
}
