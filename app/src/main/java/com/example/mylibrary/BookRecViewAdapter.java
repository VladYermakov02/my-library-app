package com.example.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.mylibrary.BookActivity.BOOK_ID_KEY;

// make sure to pass ViewHolder from my package

/**
 * \brief recViewAdapter for books
 */
public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> {
    private static final String TAG = "BookRecViewAdapter";

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

    /// \brief booksList that is shown to a user
    /// init right away to make sure it's never null
    private ArrayList<Book> booksList = new ArrayList<>();

    // \brief context for Glide, because it needs context
    private final Context mContext;
    // \brief a parent activity
    private final String parentActivity;

    /**
     *
     * @param mContext context where to show
     * @param parentActivity parent activity of the adapter
     */
    public BookRecViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    /**
     *
     * @param parent parent ViewGroup
     * @param viewType type of view
     * @return new ViewHolder(view)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new ViewHolder(view);
    }

    /**
     * \brief override of onBindViewHolder
     * sets data, expands or shrinks a book card, set onClick to delete button
     * @param holder current holder
     * @param position position(id) of pressed button
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        setBookCardData(holder, position);

        if (booksList.get(position).isExpanded()) {
            expandBookCardView(holder, position);
            removeBookFromListOnClick(holder, position);
        } else {
            shrinkBookCardView(holder);
        }
    }

    /**
     * \brief returns size of booksList (bookList that is in adapter right now)
     * @return size of booksList
     */
    @Override
    public int getItemCount() {
        return booksList.size();
    }

    /**
     * \brief sets books from some list to current local list
     * @param books books are passed from some list
     */
    public void setBooks(ArrayList<Book> books) {
        this.booksList = books;
        // to refresh data in Recycler View
        notifyDataSetChanged();
    }

    /**
     * \brief sets data to book card
     * @param holder current viewHolder
     * @param position pos(id) of current book card
     */
    private void setBookCardData(@NonNull ViewHolder holder, final int position) {
        holder.txtBookName.setText(booksList.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(booksList.get(position).getImageURL())
                .into(holder.imgBook);

        holder.parentCardView.setOnClickListener(v -> setIdToIntentCardViewOnClick(position));
        holder.txtAuthor.setText(booksList.get(position).getAuthor());
        holder.txtShortDesc.setText(booksList.get(position).getShortDesc());
    }

    /**
     * \brief puts current id of a book to intent and moves user to BookActivity
     * @param position pos(id) of current book card
     */
    private void setIdToIntentCardViewOnClick(final int position) {
        Toast.makeText(mContext, booksList.get(position).getName() + " Selected", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, BookActivity.class);
        intent.putExtra(BOOK_ID_KEY, booksList.get(position).getId());
        mContext.startActivity(intent);
    }

    /**
     * \brief shrinks book card
     * @param holder current holder
     */
    private void shrinkBookCardView(@NonNull ViewHolder holder) {
        // expanding animation of CardView
        TransitionManager.beginDelayedTransition(holder.parentCardView);

        holder.expandedRelLayout.setVisibility(View.GONE);
        holder.downArrow.setVisibility(View.VISIBLE);
    }

    /**
     * \brief expands book card
     * @param holder current viewHolder
     * @param position pos(id) of current book card
     */
    private void expandBookCardView(@NonNull ViewHolder holder, final int position) {
        // make sure to select Androidx folder of TransitionManager
        // expanding animation of CardView
        TransitionManager.beginDelayedTransition(holder.parentCardView);

        if (parentActivity.equals(ALL_BOOKS_KEY)) {
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        } else {
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * \brief deletes book from list
     * @param holder current holder (book card)
     * @param position pos(id) of current book card
     */
    private void removeBookFromListOnClick(@NonNull ViewHolder holder, final int position) {
        holder.btnDelete.setOnClickListener(v -> {
            // Alert Dialog whether the user wants to delete a book
            AlertDialog.Builder alertDiagBuilder = new AlertDialog.Builder(mContext);

            alertDiagBuilder.setMessage("Are you sure you want to delete " + booksList.get(position).getName() + "?");
            alertDiagBuilder.setPositiveButton("Yes", (dialog, which) -> removeBookFromProperList(position));
            alertDiagBuilder.setNegativeButton("No", (dialog, which) -> {
            });
            alertDiagBuilder.create().show();
        });
    }

    /**
     * \brief deletes book from proper list
     * @param position pos(id) of current book card
     */
    private void removeBookFromProperList(final int position) {
        /*if (parentActivity.equals(ALL_BOOKS_KEY)
                && Utils.getInstance(mContext).removeFromAllBooks(booksList.get(position))) {
            Toast.makeText(mContext, "Book from every list", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }*/
        if (parentActivity.equals(ALREADY_READ_BOOKS)
                && Database.getInstance(mContext).removeFromAlreadyRead(booksList.get(position))) {
            Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        } else if (parentActivity.equals(WANT_TO_READ_BOOKS)
                && Database.getInstance(mContext).removeFromWantToRead(booksList.get(position))) {
            Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        } else if (parentActivity.equals(CURRENTLY_READING_BOOKS)
                && Database.getInstance(mContext).removeFromCurrentlyReading(booksList.get(position))) {
            Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        } else if (parentActivity.equals(FAVORITE_BOOK)
                && Database.getInstance(mContext).removeFromFavoriteBooks(booksList.get(position))) {
            Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "Book has bot been removed", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }
    }

    /**
     * \brief ViewHolder - one book card
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /// all data for filling book data
        private final CardView parentCardView;
        private final ImageView imgBook;
        private final TextView txtBookName, txtAuthor, txtShortDesc;
        private final ImageView downArrow, upArrow;
        private final RelativeLayout expandedRelLayout;

        private final TextView btnDelete;

        /**
         * \brief initiates all data
         * @param itemView current book card
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCardView = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtBookName = itemView.findViewById(R.id.txtBookName);

            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtShortDesc = itemView.findViewById(R.id.txtShortDesc);

            btnDelete = itemView.findViewById(R.id.btnDelete);

            setUpArrowOnClick();
            setDownArrowOnClick();
        }

        /**
         * \brief sets current book's parameter that says book card is expanded
         */
        private void setUpArrowOnClick() {
            upArrow.setOnClickListener(v -> {
                // getAdapterPosition() - id of the book that is currently "active"
                Book book = booksList.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
        /**
         * \brief sets current book's parameter that says book card is "shrinked"
         */
        private void setDownArrowOnClick() {
            downArrow.setOnClickListener(v -> {
                // getAdapterPosition() - id of the book that is currently "active"
                Book book = booksList.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
