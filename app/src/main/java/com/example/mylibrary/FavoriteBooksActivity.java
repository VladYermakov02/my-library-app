package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

/**
 * \brief override of onBackPressed method
 * clears onBackPressed "stack"
 */
public class FavoriteBooksActivity extends AppCompatActivity {
    /// \brief a key that represents this list in adapter
    private static final String FAVORITE_BOOK = "favorite_books";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_books);

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
        RecyclerView recyclerView = findViewById(R.id.favorBookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, FAVORITE_BOOK);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter.setBooks(Utils.getInstance().getAlreadyReadBooks());

        adapter.setBooks(Database.getInstance(this).getFavoriteBooks());
    }
}