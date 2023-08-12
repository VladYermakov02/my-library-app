package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

/**
 * \brief class shows list with already read books
 * extends AppCompatActivity
 */
public class CurrentlyReadingBooksActivity extends AppCompatActivity {
    /// \brief a key that represents this list in adapter
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_reading_books);

        setBookThroughAdapter();
    }

    // triggered when the user clicks on the back button

    /**
     * \brief override of onBackPressed method
     * clears onBackPressed "stack"
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        // clearing backstack of the back button
        // and defining a new task
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * \brief sets books through adapter to list
     */
    private void setBookThroughAdapter() {
        RecyclerView recyclerView = findViewById(R.id.currBookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, CURRENTLY_READING_BOOKS);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter.setBooks(Utils.getInstance().getAlreadyReadBooks());

        adapter.setBooks(Database.getInstance(this).getCurrentlyReadingBooks());
    }
}