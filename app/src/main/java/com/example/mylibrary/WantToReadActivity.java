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
public class WantToReadActivity extends AppCompatActivity {
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_read);

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
        RecyclerView recyclerView = findViewById(R.id.wantToBooksRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, WANT_TO_READ_BOOKS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter.setBooks(Utils.getInstance().getAlreadyReadBooks());

        adapter.setBooks(Database.getInstance(this).getWantToReadBooks());
    }
}