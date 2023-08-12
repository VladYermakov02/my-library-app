package com.example.mylibrary;

/**
 * \brief contains all info about book
 */
public class Book {
    private int id;
    private String name;
    private String author;
    private int pages;
    private String imageURL;
    private String shortDesc;
    private String longDesc;
    private String myReview;
    private boolean isExpanded;

    /**
     * \brief sets default values
     */
    public Book() {
        id = -1;
        name = "";
        author = "";
        pages = -1;
        shortDesc = "";
        longDesc = "";
        myReview = "";
        imageURL = "";
    }

    /**
     * \brief sets valus to a book
     * @param id id
     * @param name book name
     * @param author author
     * @param pages pages number
     * @param shortDesc short description
     * @param longDesc long description
     * @param myReview user's review
     * @param imageURL imageURL
     */
    public Book(int id, String name, String author, int pages, String shortDesc, String longDesc, String myReview, String imageURL) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.imageURL = imageURL;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.myReview = myReview;
        isExpanded = false;
    }

    /**
     * \brief sets a new book to set to an old one
     * @param newBook a new book to set to an old one
     */
    public void setBook(Book newBook) {
        id = newBook.getId();
        this.name = newBook.getName();
        this.author = newBook.getAuthor();
        this.pages = newBook.getPages();
        this.shortDesc = newBook.getShortDesc();
        this.longDesc = newBook.getLongDesc();
        this.myReview = newBook.getMyReview();
        this.imageURL = newBook.getImageURL();
    }

    public String getMyReview() {
        return myReview;
    }

    public void setMyReview(String myReview) {
        this.myReview = myReview;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", imageURL='" + imageURL + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", myReview='" + myReview + '\'' +
                ", isExpanded=" + isExpanded +
                '}';
    }
}