package com.lsx.finalhomework.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsx.finalhomework.NWImageView;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Book;

import java.util.List;

public class MyBookRecyclerViewAdapter extends RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    private final List<Book> bookList;

    OnItemClickListener mItemClickListener;

    public MyBookRecyclerViewAdapter(List<Book> items) {
        bookList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Book item = bookList.get(position);
        holder.itemView.setTag(item.getId());
        holder.nameView.setText(item.getName());
        holder.authorView.setText(item.getAuthor());
        holder.priceView.setText(String.format("%s元", item.getPrice()));
        holder.imgView.setImageURL(item.getImgUrl());
        holder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(v));
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView authorView;
        public final NWImageView imgView;
        public final TextView priceView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.text_name);
            authorView = itemView.findViewById(R.id.text_author);
            imgView = itemView.findViewById(R.id.list_image);
            priceView = itemView.findViewById(R.id.text_price);
        }
    }
}