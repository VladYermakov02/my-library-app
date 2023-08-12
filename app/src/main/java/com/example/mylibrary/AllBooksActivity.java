package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * \brief class shows list with all books
 * user can press a button here to add a new book to list
 * extends AppCompatActivity
 */
public class AllBooksActivity extends AppCompatActivity {
    private static final String ALL_BOOKS_KEY = "all_books";
    private FloatingActionButton fabAddNewBook;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        setUpUpBackButton();
        getBooksToRecView();
        addNewBook();
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
     * \brief override of up-back button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // home is the name of the up-back button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * \brief sets books to adapter
     */
    private void getBooksToRecView() {
        RecyclerView booksRecView = findViewById(R.id.booksRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, ALL_BOOKS_KEY);

        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Database.getInstance(this).getAllBooks());
    }

    /**
     * \brief Set whether home should be displayed as an "up" affordance.
     * Set this to true if selecting "home" returns up by a single level in your UI rather than back to the top level or front page.
     * To set several display options at once, see the setDisplayOptions methods.
     */
    private void setUpUpBackButton() {
        // The Up-Back Button is the button on the upper left corner of the screen (usually)
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    /**
     * \brief moves to activity where a user can input info for a new book
     */
    private void addNewBook() {
        fabAddNewBook = findViewById(R.id.fabAddNewBook);
        fabAddNewBook.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddNewBookActivity.class);
            startActivity(intent);
        });
    }
}