package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * \brief outputs everything about a book to a user
 * through this class a user can perform changes on a book
 * also, can add the book to some list
 * */
public class BookActivity extends AppCompatActivity {
    private static final String TAG = "BookActivity";

    /// \brief String that holds an id of a book coming from intent
    public static final String BOOK_ID_KEY = "bookId";

    /// \brief TextViews output all information that sets / changes a user
    private TextView txtPageNumShow, txtAuthorNameShow, txtBookName, txtLongDesc, txtMyReview;
    /// \brief these buttons add a book to a proper list to database
    private Button btnAlreadyRead, btnAddToCurrentlyReading, btnAddToWantToRead, btnAddToFavorite;
    /// \brief button moves users to activity where they can change information about the book
    private Button btnChangeBookInfo;
    /// \brief holds an image URL and outputs in as an image
    private ImageView bookImage;
    /// \brief book that is coming from intent
    private Book incomingBook;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();
        catchIncomingBook();

        setData(this.incomingBook);

        addBookToLists(this.incomingBook);
        changeBookInfoOnClick(this.incomingBook);
    }

    /**
     * inits all Views
     */
    private void initViews() {
        txtAuthorNameShow = findViewById(R.id.txtAuthorNameShow);
        txtBookName = findViewById(R.id.txtBookName);
        txtPageNumShow = findViewById(R.id.txtPageNumShow);
        txtMyReview = findViewById(R.id.txtMyReview);
        txtLongDesc = findViewById(R.id.txtLongDesc);

        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
        btnChangeBookInfo = findViewById(R.id.btnChangeBookInfo);

        bookImage = findViewById(R.id.bookImage);
    }

    /**
     * \brief catches an incoming book from intent
     */
    private void catchIncomingBook() {
        // we're catching the intent that is coming here
        Intent intent = getIntent();
        if (intent != null) {
            Log.d(TAG, "onCreate: intent != null");

            // bookId - name has to be the same
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Log.d(TAG, "onCreate: bookId != -1");
                Book incomingBook = Database.getInstance(this).getBookById(bookId);
                if (incomingBook != null) {
                    Log.d(TAG, "onCreate: incomingBook != null");
                    this.incomingBook = incomingBook;
                    return;
                }
            }
        }
        this.incomingBook = null;
    }

    /**
     * \brief adds the book to needed list
     * @param book - can be added to a list
     */
    private void addBookToLists(final Book book) {
        if (book != null) {
            addBookToAlreadyReadOnClick(this.incomingBook);
            addBookToWantToReadOnClick(this.incomingBook);
            addBookToCurrentlyReadingOnClick(this.incomingBook);
            addBookToFavoriteOnClick(this.incomingBook);
        } else {
            Toast.makeText(this, "Book doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * \brief Enable and Disable button, Add the book to Already Read Books ArrayList
     * @param book - book that can be added
     */
    private void addBookToAlreadyReadOnClick(final Book book) {
        if (whetherBookExistsInAlreadyReadBooks(book)) {
            // if added already
            btnAlreadyRead.setEnabled(false);
        } else {
            btnAlreadyRead.setOnClickListener(v -> {
                if (Database.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                    Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                    moveUserToActivity(AlreadyReadBookActivity.class);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * \brief Enable and Disable button, Add the book to Want To Read ArrayList
     * @param book - book that can be added
     */
    private void addBookToWantToReadOnClick(final Book book) {
        if (whetherBookExistsInWantToRead(book)) {
            // if added already
            btnAddToWantToRead.setEnabled(false);
        } else {
            btnAddToWantToRead.setOnClickListener(v -> {
                if (Database.getInstance(BookActivity.this).addToWantToRead(book)) {
                    Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                    moveUserToActivity(WantToReadActivity.class);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * \brief Enable and Disable button, Add the book to Currently Reading Books ArrayList
     * @param book - book that can be added
     */
    private void addBookToCurrentlyReadingOnClick(final Book book) {
        if (whetherBookExistsInCurrentlyReadingBooks(book)) {
            // if added already
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(v -> {
                if (Database.getInstance(BookActivity.this).addToCurrentlyReading(book)) {
                    Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                    moveUserToActivity(CurrentlyReadingBooksActivity.class);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * \brief Enable and Disable button, Add the book to Favorite Books ArrayList
     * @param book - book that can be added
     */
    private void addBookToFavoriteOnClick(final Book book) {
        if (whetherBookExistsInFavoriteBooks(book)) {
            // if added already
            btnAddToFavorite.setEnabled(false);
        } else {
            btnAddToFavorite.setOnClickListener(v -> {
                if (Database.getInstance(BookActivity.this).addToFavoriteBooks(book)) {
                    Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                    moveUserToActivity(FavoriteBooksActivity.class);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * \brief sets OnClick to button that moves a user to an activity where incomingBook can be changed
     * @param incomingBook - goes to DB to be saved fro changing later on
     */
    private void changeBookInfoOnClick(final Book incomingBook) {
        if (incomingBook != null) {
            btnChangeBookInfo.setOnClickListener(v -> {
                Database.setBookToChange(incomingBook);
                Toast.makeText(BookActivity.this, "Input changed information.", Toast.LENGTH_SHORT).show();
                moveUserToActivity(ChangeBookInfoActivity.class);
            });
        } else {
            Toast.makeText(this, "Book doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * \brief tells whether the book exist in proper list
     * @param book the book that is being searched
     * @return true if book exists
     */
    private boolean whetherBookExistsInAlreadyReadBooks(final Book book) {
        ArrayList<Book> alreadyReadBooks = Database.getInstance(this).getAlreadyReadBooks();
        return whetherBookExistsInList(alreadyReadBooks, book);
    }

    /**
     * \brief tells whether the book exist in proper list
     * @param book the book that is being searched
     * @return true if book exists
     */
    private boolean whetherBookExistsInCurrentlyReadingBooks(final Book book) {
        ArrayList<Book> currentlyReading = Database.getInstance(this).getCurrentlyReadingBooks();
        return whetherBookExistsInList(currentlyReading, book);
    }

    /**
     * \brief tells whether the book exist in proper list
     * @param book the book that is being searched
     * @return true if book exists
     */
    private boolean whetherBookExistsInWantToRead(final Book book) {
        ArrayList<Book> wantToReadBooks = Database.getInstance(this).getWantToReadBooks();
        return whetherBookExistsInList(wantToReadBooks, book);
    }

    /**
     * \brief tells whether the book exist in proper list
     * @param book the book that is being searched
     * @return true if book exists
     */
    private boolean whetherBookExistsInFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Database.getInstance(this).getFavoriteBooks();
        return whetherBookExistsInList(favoriteBooks, book);
    }

    /**
     * \brief tells whether the book exist in proper list
     * @param book the book that is being searched
     * @param books - a list where to search
     * @return true if book exists
     */
    private boolean whetherBookExistsInList(ArrayList<Book> books, final Book book) {
        boolean existsInList = false;
        for (Book b : books) {
            if (b.getId() == book.getId()) {
                existsInList = true;
                break;
            }
        }
        return existsInList;
    }

    /**
     * \brief moves a user to needed activity
     * @param classToMove moves user to this class
     */
    private void moveUserToActivity(Class classToMove) {
        Intent intent = new Intent(BookActivity.this, classToMove);
        // startActivity exists in every activity
        // means to move a user from BookActivity to FavoriteBooksActivity
        startActivity(intent);
    }

    /**
     * \brief sets book's data to users screen
     * @param book the book to be shown to a user
     */
    @SuppressLint("SetTextI18n")
    private void setData(Book book) {
        if (book != null) {
            Log.d(TAG, "setData");
            txtBookName.setText(book.getName());
            txtAuthorNameShow.setText(book.getAuthor());
            txtPageNumShow.setText(String.valueOf(book.getPages()));
            txtLongDesc.setText(book.getLongDesc());
            txtMyReview.setText(book.getMyReview());
            Glide.with(this)
                    .asBitmap().load(book.getImageURL())
                    .into(bookImage);
        } else {
            Toast.makeText(this, "Book doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }
}