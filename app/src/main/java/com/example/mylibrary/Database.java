package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * \brief program's database
 */
public class Database {
    /// there'll be only once instance of a class
    private static Database instance;

    /// \brief key that represents all_books_activity
    private static final String ALL_BOOKS_KEY = "all_books";
    /// \brief key that represents already_read_books_activity
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    /// \brief key that represents want_to_read_books_activity
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
    /// \brief key that represents currently_reading_books_activity
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    /// \brief key that represents favorite_books_activity
    private static final String FAVORITE_BOOK = "favorite_books";

    /// \brief temp ArrayList<Book> for operations
    private static ArrayList<Book> books;
    /// \brief book that can be changed, wipes after user exits the app
    private static Book bookToChange;

    /// \brief moves data to SharedPreferences
    private static Gson gson;
    /// \brief all operations of SharedPreferences go through editor
    private static SharedPreferences.Editor editor;
    /// \brief sharedPreferences is our database
    private final SharedPreferences sharedPreferences;

    /**
     * \brief just initialises all data
     * @param context
     */
    private Database(Context context) {
        // MODE_PRIVATE - means that SharedPreferences is only for my app
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (getAllBooks() == null) {
            initData();
        }

        editor = sharedPreferences.edit();
        gson = new Gson();

        putEmptyStringToEditorIfListEmpty(editor, gson);
    }

    // synchronized - doesn't let different threads create 2nd instance of a class

    /**
     * /brief gets instance
     * @param context
     * @return instance, can be only one at the time
     */
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            return new Database(context);
        }
        return instance;
    }

    /**
     * /brief gets bookToChange
     * @return book, needs for ChangeBookInfoActivity
     */
    public static Book getBookToChange() {
        return bookToChange;
    }

    /**
     * /brief sets bookToChange to save it
     * @param bookToChange
     */
    public static void setBookToChange(final Book bookToChange) {
        Database.bookToChange = bookToChange;
    }

    /**
     * /brief sets new book instead of an old one
     * @param newBook book that will replace an old one
     * @param index index of book that needs to be replaced
     * @return true if new book is set successfully
     */
    public boolean setAnotherBookToAllBooks(Book newBook, int index) {
        books = getAllBooks();
        if (books != null && newBook != null) {
            books.get(index).setBook(newBook);
            return setNewAllBooksList(books, ALL_BOOKS_KEY);
        }
        return false;
    }

    public ArrayList<Book> getAllBooks() {
        return getBooksFromJson(ALL_BOOKS_KEY);
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        return getBooksFromJson(ALREADY_READ_BOOKS);
    }

    public ArrayList<Book> getWantToReadBooks() {
        return getBooksFromJson(WANT_TO_READ_BOOKS);
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        return getBooksFromJson(CURRENTLY_READING_BOOKS);
    }

    public ArrayList<Book> getFavoriteBooks() {
        return getBooksFromJson(FAVORITE_BOOK);
    }

    /**
     * \brief gets book by id
     * @param id of needed book
     * @return found book
     */
    public Book getBookById(int id) {
        books = getAllBooks();
        if (books != null) {
            for (Book b : books) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    /**
     * \brief needs to pass key of AllBooksList
     * @param book book which will be added
     * @return true if added
     */
    public boolean addToAllBooks(Book book) {
        books = getAllBooks();
        return addBookToList(books, book, ALL_BOOKS_KEY);
    }

    /**
     * \brief needs to pass key of AlreadyReadList
     * @param book book which will be added
     * @return true if added
     */
    public boolean addToAlreadyRead(Book book) {
        books = getAlreadyReadBooks();
        return addBookToList(books, book, ALREADY_READ_BOOKS);
    }

    /**
     * \brief needs to pass key of WantToReadList
     * @param book book which will be added
     * @return true if added
     */
    public boolean addToWantToRead(Book book) {
        books = getWantToReadBooks();
        return addBookToList(books, book, WANT_TO_READ_BOOKS);
    }

    /**
     * \brief needs to pass key of CurrentlyReadingList
     * @param book book which will be added
     * @return true if added
     */
    public boolean addToCurrentlyReading(Book book) {
        books = getCurrentlyReadingBooks();
        return addBookToList(books, book, CURRENTLY_READING_BOOKS);
    }

    /**
     * \brief needs to pass key of FavoriteBooksList
     * @param book book which will be added
     * @return true if added
     */
    public boolean addToFavoriteBooks(Book book) {
        books = getFavoriteBooks();
        return addBookToList(books, book, FAVORITE_BOOK);
    }

    /**
     * \brief removes book from all lists
     * @param book book which will be deleted
     * @return true if removed
     */
    public boolean removeFromAllBooks(Book book) {
        books = getAllBooks();
        ArrayList<Book> alreadyReadBooks, currentlyReadingBooks, wantToReadBooks, favoriteBooks;

        alreadyReadBooks = getAlreadyReadBooks();
        currentlyReadingBooks = getCurrentlyReadingBooks();
        wantToReadBooks = getWantToReadBooks();
        favoriteBooks = getFavoriteBooks();

        removeBookFromList(alreadyReadBooks, book, ALREADY_READ_BOOKS);
        removeBookFromList(currentlyReadingBooks, book, CURRENTLY_READING_BOOKS);
        removeBookFromList(wantToReadBooks, book, WANT_TO_READ_BOOKS);
        removeBookFromList(favoriteBooks, book, FAVORITE_BOOK);
        return removeBookFromList(books, book, ALL_BOOKS_KEY);
    }

    /**
     * \brief removes book from AlreadyReadList
     * @param book book which will be deleted
     * @return true if removed
     */
    public boolean removeFromAlreadyRead(Book book) {
        books = getAlreadyReadBooks();
        return removeBookFromList(books, book, ALREADY_READ_BOOKS);
    }

    /**
     * \brief removes book from WantToReadList
     * @param book book which will be deleted
     * @return true if removed
     */
    public boolean removeFromWantToRead(Book book) {
        books = getWantToReadBooks();
        return removeBookFromList(books, book, WANT_TO_READ_BOOKS);
    }

    /**
     * \brief removes book from CurrentlyReadingList
     * @param book book which will be deleted
     * @return true if removed
     */
    public boolean removeFromCurrentlyReading(Book book) {
        books = getCurrentlyReadingBooks();
        return removeBookFromList(books, book, CURRENTLY_READING_BOOKS);
    }

    /**
     * \brief removes book from FavoriteBooksList
     * @param book book which will be deleted
     * @return true if removed
     */
    public boolean removeFromFavoriteBooks(Book book) {
        books = getFavoriteBooks();
        return removeBookFromList(books, book, FAVORITE_BOOK);
    }

    /**
     * \brief puts empty string to every empty list
     * @param editor
     * @param gson
     */
    private void putEmptyStringToEditorIfListEmpty(SharedPreferences.Editor editor, Gson gson) {
        if (getAlreadyReadBooks() == null) {
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if (getWantToReadBooks() == null) {
            editor.putString(WANT_TO_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if (getCurrentlyReadingBooks() == null) {
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if (getFavoriteBooks() == null) {
            editor.putString(FAVORITE_BOOK, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    /**
     * \brief initiates all data
     */
    private void initData() {
        //TODO: Delete this after final test
        books = new ArrayList<>();
        bookToChange = new Book();
        books.add(new Book(1, "1Q84", "Haruki Murakami", 1350, "A work of maddeling brilliance",
                "Long Description The central concern of The Myth of Sisyphus is what Camus calls \"the absurd.\".",
                "My review The central concern of The Myth of Sisyphus is what Camus calls \"the absurd.\".",
                "https://s1.livelib.ru/boocover/1001782052/o/bc8d/Haruki_Murakami__1Q84.jpeg"));
        books.add(new Book(2, "The Myth of Sisyphus", "Albert Camus", 250, "A work of maddeling brilliance",
                "Long Description \"the absurd.\" Camus claims that there is a fundamental conflict between what we want from the universe (whether it be meaning, order, or reasons) and what we find in the universe (formless chaos).",
                "My review \"the absurd.\" Camus claims that there is a fundamental conflict between what we want from the universe (whether it be meaning, order, or reasons) and what we find in the universe (formless chaos).",
                "https://upload.wikimedia.org/wikipedia/en/7/75/Le_Mythe_de_Sisyphe.jpg"));

        editor = sharedPreferences.edit();
        gson = new Gson();
        // converts array to a string
        //String bookText = gson.toJson(books);
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();
    }

    /**
     * \brief gets string from gson
     * @param listKey key that says from which list take bookList
     * @return ArrayList of books
     */
    private ArrayList<Book> getBooksFromJson(final String listKey) {
        gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(listKey, null), type);
    }

    /**
     * \brief puts new string to json
     * @param books book which will be added
     * @param listKey needed key that represents that list
     * @return true if added
     */
    private boolean setNewAllBooksList(List<Book> books, final String listKey) {
        if (books != null) {
            gson = new Gson();
            editor = sharedPreferences.edit();
            // removes all old books
            editor.remove(listKey);
            // puts a string which contains all old book + a new one
            editor.putString(listKey, gson.toJson(books));
            editor.commit();
            return true;
        }
        return false;
    }

    /**
     * \brief adds book to a list
     * @param books ArrayList to which book'll be added
     * @param book book which will be added
     * @param listKey key that represents needed ArrayList
     * @return true if added
     */
    private boolean addBookToList(List<Book> books, Book book, final String listKey) {
        if (books != null) {
            if (books.add(book)) {
                gson = new Gson();
                editor = sharedPreferences.edit();
                // removes all old books
                editor.remove(listKey);
                // puts a string which contains all old book + a new one
                editor.putString(listKey, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    /**
     * \brief removes book from a list
     * @param books ArrayList in which book'll be removed
     * @param book book which will be removed
     * @param listKey key that represents needed ArrayList
     * @return true if removed
     */
    private boolean removeBookFromList(List<Book> books, Book book, final String listKey) {
        if (books != null) {
            // books.remove(book) - is not gonna work because references are not equal, even though values are
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    // passing 'b' we don't check the references as if we pass 'book'
                    if (books.remove(b)) {
                        gson = new Gson();
                        editor = sharedPreferences.edit();
                        editor.remove(listKey);
                        editor.putString(listKey, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}