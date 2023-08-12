package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

public class ChangeBookInfoActivity extends AppCompatActivity {
    private static final String TAG = "ChangeBookInfoActivity";

    /// \brief fields for user to change
    private EditText edtTxtBookNameChange, edtTxtAuthorNameChange, edtTxtPageNumChange, edtTxtShortDescChange, edtTxtLongDescChange, edtTxtReviewChange, edtTxtUrlChange;
    /// \brief textFields for user that come out if some field's empty
    private TextView txtWarnBookNameChange, txtWarnAuthorNameChange, txtWarnPageNumChange, txtWarnShortDescChange, txtWarnLongDescChange, txtWarnReviewChange, txtWarnUrlChange;
    /// \brief button that submits all data
    private Button btnSubmitBookInfoChange;
    /// \brief old book that will be changed
    private Book bookToChange;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_book_info);

        initViews();

        bookToChange = Database.getBookToChange();
        setPreviousData();
        changeBookInfo();
    }

    /**
     * \brief inits all views
     */
    private void initViews() {
        edtTxtBookNameChange = findViewById(R.id.edtTxtBookNameChange);
        edtTxtAuthorNameChange = findViewById(R.id.edtTxtAuthorNameChange);
        edtTxtPageNumChange = findViewById(R.id.edtTxtPageNumChange);
        edtTxtShortDescChange = findViewById(R.id.edtTxtShortDescChange);
        edtTxtLongDescChange = findViewById(R.id.edtTxtLongDescChange);
        edtTxtReviewChange = findViewById(R.id.edtTxtReviewChange);
        edtTxtUrlChange = findViewById(R.id.edtTxtUrlChange);

        txtWarnBookNameChange = findViewById(R.id.txtWarnBookNameChange);
        txtWarnAuthorNameChange = findViewById(R.id.txtWarnAuthorNameChange);
        txtWarnPageNumChange = findViewById(R.id.txtWarnPageNumChange);
        txtWarnShortDescChange = findViewById(R.id.txtWarnShortDescChange);
        txtWarnLongDescChange = findViewById(R.id.txtWarnLongDescChange);
        txtWarnReviewChange = findViewById(R.id.txtWarnReviewChange);
        txtWarnUrlChange = findViewById(R.id.txtWarnUrlChange);

        btnSubmitBookInfoChange = findViewById(R.id.btnSubmitBookInfoChange);
    }

    /**
     * \brief sets old book' data to editTexts
     * so user doesn't input in one more time
     * @return true if current book != null
     */
    @SuppressLint("SetTextI18n")
    private boolean setPreviousData() {
        if (bookToChange != null) {
            edtTxtBookNameChange.setText(bookToChange.getName());
            edtTxtAuthorNameChange.setText(bookToChange.getAuthor());
            edtTxtPageNumChange.setText(Integer.toString(bookToChange.getPages()));
            edtTxtShortDescChange.setText(bookToChange.getShortDesc());
            edtTxtLongDescChange.setText(bookToChange.getLongDesc());
            edtTxtReviewChange.setText(bookToChange.getMyReview());
            edtTxtUrlChange.setText(bookToChange.getImageURL());
            return true;
        }
        Toast.makeText(this, "Book doesn't exist (setPreviousData)", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * \brief sets new data to old book
     */
    private void changeBookInfo() {
        btnSubmitBookInfoChange.setOnClickListener(v -> {
            if (validateData()) {
                if (bookToChange != null) {

                    int pageNum = 0;
                    if(isInteger(edtTxtPageNumChange.getText().toString()))
                        pageNum = Integer.parseInt(edtTxtPageNumChange.getText().toString());
                    Log.d(TAG, "changeBookInfo: pageNum = " + pageNum);

                    Book changedBook = new Book(
                            bookToChange.getId(),
                            edtTxtBookNameChange.getText().toString(),
                            edtTxtAuthorNameChange.getText().toString(),
                            pageNum,
                            edtTxtShortDescChange.getText().toString(),
                            edtTxtLongDescChange.getText().toString(),
                            edtTxtReviewChange.getText().toString(),
                            edtTxtUrlChange.getText().toString());
                    Database.getInstance(this).setAnotherBookToAllBooks(changedBook, bookToChange.getId() - 1);
                    Toast.makeText(this, "The book has been changed.", Toast.LENGTH_SHORT).show();
                    moveUserTo(AllBooksActivity.class);
                } else {
                    Toast.makeText(this, "Book doesn't exist (changeBookInfo)", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Fill up all data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // if a number - true
    private boolean isInteger(String str) {
        for (int i = 0; i < str.length(); i++)
            if (!Character.isDigit(str.charAt(i)))
                return false;
        return true;
    }

    /*private boolean isInteger(String str) {
        return isInteger(str, 10);
    }*/

    /*private boolean isInteger(String str, int radix) {
        Scanner scan = new Scanner(str.trim());
        if (!scan.hasNextInt(radix))
            return false;
        // we know it starts with a valid int, now make sure
        // there's nothing left
        scan.nextInt(radix);
        return !scan.hasNext();
    }*/
    /*private boolean isInteger(String str, int radix) {
        if (str.isEmpty())
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 && str.charAt(i) == '-') {
                if (str.length() == 1)
                    return false;
                else
                    continue;
            }
            if (Character.digit(str.charAt(i), radix) < 0)
                return false;
        }
        return true;
    }*/

    /**
     *
     * @return true if user inputs everything
     */
    @SuppressLint("SetTextI18n")
    private boolean validateData() {
        if (edtTxtBookNameChange.getText().toString().equals("")) {
            txtWarnBookNameChange.setVisibility(View.VISIBLE);
            txtWarnBookNameChange.setText("Enter book name");
            return false;
        }
        if (edtTxtAuthorNameChange.getText().toString().equals("")) {
            txtWarnAuthorNameChange.setVisibility(View.VISIBLE);
            txtWarnAuthorNameChange.setText("Enter author name");
            return false;
        }
        if (edtTxtPageNumChange.getText().toString().equals("")) {
            txtWarnPageNumChange.setVisibility(View.VISIBLE);
            txtWarnPageNumChange.setText("Enter page num");
            return false;
        }
        if (edtTxtShortDescChange.getText().toString().equals("")) {
            txtWarnShortDescChange.setVisibility(View.VISIBLE);
            txtWarnShortDescChange.setText("Enter short description");
            return false;
        }
        if (edtTxtLongDescChange.getText().toString().equals("")) {
            txtWarnLongDescChange.setVisibility(View.VISIBLE);
            txtWarnLongDescChange.setText("Enter long description");
            return false;
        }
        if (edtTxtReviewChange.getText().toString().equals("")) {
            txtWarnReviewChange.setVisibility(View.VISIBLE);
            txtWarnReviewChange.setText("Enter your review");
            return false;
        }
        if (edtTxtUrlChange.getText().toString().equals("")) {
            txtWarnUrlChange.setVisibility(View.VISIBLE);
            txtWarnUrlChange.setText("Enter book image url (with .jpg or .png ending)");
            return false;
        }
        return true;
    }

    /**
     * \brief moves user to needed class
     * @param classToMove class's name where to move user
     */
    private void moveUserTo(Class classToMove) {
        Intent intent = new Intent(ChangeBookInfoActivity.this, classToMove);
        startActivity(intent);
    }
}