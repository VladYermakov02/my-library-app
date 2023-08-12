package com.example.mylibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * \brief main menu of the program
 */
public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks, btnCurrentlyReading, btnAlreadyRead, btnWantToRead, btnFavorite, btnStatistics, btnAbout;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        allBooksActivityOpen();
        alreadyReadActivityOpen();
        currentlyReadingActivityOpen();
        favoriteActivityOpen();
        wantToReadActivityOpen();
        statisticsActivityOpen();
        aboutActivityOpen();

        Database.getInstance(this);
    }

    /**
     * \brief moves user to AllBooksActivity
     */
    private void allBooksActivityOpen() {
        btnAllBooks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
            // startActivity exists in every activity
            startActivity(intent);
        });
    }

    /**
     * \brief moves user to AlreadyReadBookActivity
     */
    private void alreadyReadActivityOpen() {
        btnAlreadyRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlreadyReadBookActivity.class);
            // startActivity exists in every activity
            startActivity(intent);
        });
    }

    /**
     * \brief moves user to CurrentlyReadingBooksActivity
     */
    private void currentlyReadingActivityOpen() {
        btnCurrentlyReading.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrentlyReadingBooksActivity.class);
            // startActivity exists in every activity
            startActivity(intent);
        });
    }

    /**
     * \brief moves user to FavoriteBooksActivity
     */
    private void favoriteActivityOpen() {
        btnFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoriteBooksActivity.class);
            // startActivity exists in every activity
            startActivity(intent);
        });
    }

    /**
     * \brief moves user to WantToReadActivity
     */
    private void wantToReadActivityOpen() {
        btnWantToRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WantToReadActivity.class);
            // startActivity exists in every activity
            startActivity(intent);
        });
    }

    /**
     * \brief moves user to StatisticsActivity
     */
    private void statisticsActivityOpen() {
        btnStatistics.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            // startActivity exists in every activity
            startActivity(intent);
        });
    }

    /**
     * \brief shows some info with alertDialBuild
     */
    private void aboutActivityOpen() {
        btnAbout.setOnClickListener(v -> {
            AlertDialog.Builder alertDialBuild = new AlertDialog.Builder(MainActivity.this);
            alertDialBuild.setTitle(getString(R.string.app_name));
            alertDialBuild.setMessage("Designed and Developed By Vlad specially for DNURT");
            alertDialBuild.setNeutralButton("Dismiss", (dialog, which) -> {

            });
            alertDialBuild.create().show();
        });
    }

    /**
     * \brief inits all views
     */
    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnCurrentlyReading = findViewById(R.id.btnCurrentlyReading);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnWantToRead = findViewById(R.id.btnWantToRead);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnStatistics = findViewById(R.id.btnStatistics);
        btnAbout = findViewById(R.id.btnAbout);
    }
}

/*
* The way to make a user migrate to some website
* btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialBuild = new AlertDialog.Builder(MainActivity.this);
                alertDialBuild.setTitle(getString(R.string.app_name));
                alertDialBuild.setMessage("Designed and Developed By Vlad specially for DNURT" +
                        "\nWould you like to visit its website?:");
                alertDialBuild.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialBuild.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                        intent.putExtra("url", "http://diit.edu.ua/");
                        startActivity(intent);
                    }
                });
                alertDialBuild.create().show();
            }
        });
 */