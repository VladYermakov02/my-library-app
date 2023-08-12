package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * \brief shows statistics to a user
 */
public class StatisticsActivity extends AppCompatActivity {

    /// Views show number of pages read / total books being read
    TextView txtBooksReadNumShow, txtTotalPageNumShow;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViews();
        setDataToViews();
    }

    /**
     * inits all views
     */
    private void initViews() {
        txtBooksReadNumShow = findViewById(R.id.txtBooksReadNumShow);
        txtTotalPageNumShow = findViewById(R.id.txtTotalPageNumShow);
    }

    /**
     * \brief gets data from getAlreadyReadBooks and sets data to views
     */
    @SuppressLint("SetTextI18n")
    private void setDataToViews() {
        int pagesNum = 0, booksNum;
        ArrayList<Book> alreadyReadBooks = Database.getInstance(this).getAlreadyReadBooks();

        if(alreadyReadBooks != null) {
            for (Book book : alreadyReadBooks) {
                pagesNum += Database.getInstance(this).getBookById(book.getId()).getPages();
            }
            booksNum = Database.getInstance(this).getAlreadyReadBooks().size();

            txtBooksReadNumShow.setText(Integer.toString(booksNum));
            txtTotalPageNumShow.setText(Integer.toString(pagesNum));
        } else {
            txtBooksReadNumShow.setText("0");
            txtTotalPageNumShow.setText("0");
        }
    }
}