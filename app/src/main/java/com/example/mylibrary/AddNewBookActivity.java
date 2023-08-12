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

/**
 * \brief class fro adding a new book, extends AppCompatActivity
 */
public class AddNewBookActivity extends AppCompatActivity {
    private static final String TAG = "AddNewBookActivity";

    /// \brief user types info in edtTxts
    private EditText edtTxtBookName, edtTxtAuthorName, edtTxtPageNum, edtTxtShortDesc, edtTxtLongDesc, edtTxtReview, edtTxtUrl;
    /// \brief TextViews output errors if user doesn't inout smth
    private TextView txtWarnBookName, txtWarnAuthorName, txtWarnPageNum, txtWarnShortDesc, txtWarnLongDesc, txtWarnReview, txtWarnUrl;
    /// \brief submits all data and creates a new book
    private Button btnSubmitBookInfo;

    /**
     * \param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        initViews();
        createBook();
    }

    /**
     * \brief for initiating all views
     */
    private void initViews() {
        edtTxtBookName = findViewById(R.id.edtTxtBookName);
        edtTxtAuthorName = findViewById(R.id.edtTxtAuthorName);
        edtTxtPageNum = findViewById(R.id.edtTxtPageNum);
        edtTxtShortDesc = findViewById(R.id.edtTxtShortDesc);
        edtTxtLongDesc = findViewById(R.id.edtTxtLongDesc);
        edtTxtReview = findViewById(R.id.edtTxtReview);
        edtTxtUrl = findViewById(R.id.edtTxtUrl);

        txtWarnBookName = findViewById(R.id.txtWarnBookName);
        txtWarnAuthorName = findViewById(R.id.txtWarnAuthorName);
        txtWarnPageNum = findViewById(R.id.txtWarnPageNum);
        txtWarnShortDesc = findViewById(R.id.txtWarnShortDesc);
        txtWarnLongDesc = findViewById(R.id.txtWarnLongDesc);
        txtWarnReview = findViewById(R.id.txtWarnReview);
        txtWarnUrl = findViewById(R.id.txtWarnUrl);

        btnSubmitBookInfo = findViewById(R.id.btnSubmitBookInfo);
    }

    /**
     * \brief creates a setOnClickListener
     * on click - book creates
     */
    private void createBook() {
        btnSubmitBookInfo.setOnClickListener(v -> {
            if (validateData()) {
                int pageNum = 0;
                if (isInteger(edtTxtPageNum.getText().toString()))
                    pageNum = Integer.parseInt(edtTxtPageNum.getText().toString());
                Log.d(TAG, "createBook: pageNum = " + pageNum);

                Database.getInstance(AddNewBookActivity.this).addToAllBooks(new Book(
                        Database.getInstance(AddNewBookActivity.this).getAllBooks().size() + 1,
                        edtTxtBookName.getText().toString(),
                        edtTxtAuthorName.getText().toString(),
                        pageNum,
                        edtTxtShortDesc.getText().toString(),
                        edtTxtLongDesc.getText().toString(),
                        edtTxtReview.getText().toString(),
                        edtTxtUrl.getText().toString()));
                Toast.makeText(this, "The book has been added.", Toast.LENGTH_SHORT).show();
                moveUserTo(AllBooksActivity.class);
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
    }


    private boolean isInteger(String str, int radix) {
        Scanner scan = new Scanner(str.trim());
        if(!scan.hasNextInt(radix))
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
     * \brief validates data that inputs a user
     * false - smth is not entered
     * true - everything is ok
     *
     * @return boolean
     */
    @SuppressLint("SetTextI18n")
    private boolean validateData() {
        if (edtTxtBookName.getText().toString().equals("")) {
            txtWarnBookName.setVisibility(View.VISIBLE);
            txtWarnBookName.setText("Enter book name");
            return false;
        }
        if (edtTxtAuthorName.getText().toString().equals("")) {
            txtWarnAuthorName.setVisibility(View.VISIBLE);
            txtWarnAuthorName.setText("Enter author name");
            return false;
        }
        if (edtTxtPageNum.getText().toString().equals("")) {
            txtWarnPageNum.setVisibility(View.VISIBLE);
            txtWarnPageNum.setText("Enter page num");
            return false;
        }
        if (edtTxtShortDesc.getText().toString().equals("")) {
            txtWarnShortDesc.setVisibility(View.VISIBLE);
            txtWarnShortDesc.setText("Enter short description");
            return false;
        }
        if (edtTxtLongDesc.getText().toString().equals("")) {
            txtWarnLongDesc.setVisibility(View.VISIBLE);
            txtWarnLongDesc.setText("Enter long description");
            return false;
        }
        if (edtTxtReview.getText().toString().equals("")) {
            txtWarnReview.setVisibility(View.VISIBLE);
            txtWarnReview.setText("Enter your review");
            return false;
        }
        if (edtTxtUrl.getText().toString().equals("")) {
            txtWarnUrl.setVisibility(View.VISIBLE);
            txtWarnUrl.setText("Enter book image url (with .jpg or .png ending)");
            return false;
        }
        return true;
    }

    /**
     * \brief moves a user to classToMove (only if class is an activity)
     *
     * @param classToMove
     */
    private void moveUserTo(Class classToMove) {
        Intent intent = new Intent(AddNewBookActivity.this, classToMove);
        startActivity(intent);
    }
}