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

import java.util.ArrayList;
import java.util.List;

public class MyBookRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum ViewType {
        CONTENT,
        HEADER
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    private ArrayList bookList;

    OnItemClickListener mItemClickListener;

    public MyBookRecyclerViewAdapter(ArrayList items) {
        bookList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ViewType.CONTENT.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_fragment_item, parent, false);
            return new ContentViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ViewType.CONTENT.ordinal()) {
            Book item = (Book) bookList.get(position);
            ContentViewHolder contentHolder = (ContentViewHolder) holder;
            contentHolder.itemView.setTag(item.getId());
            contentHolder.nameView.setText(item.getName());
            contentHolder.authorView.setText(item.getAuthor());
            contentHolder.priceView.setText(String.format("%s元", item.getPrice()));
            contentHolder.imgView.setImageURL(item.getImgUrl());
            contentHolder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(v));
        } else {
            String header = ((Book.Category) bookList.get(position)).getName();
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerTextView.setText(header);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return bookList.get(position) instanceof Book ? ViewType.CONTENT.ordinal() : ViewType.HEADER.ordinal();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView authorView;
        public final NWImageView imgView;
        public final TextView priceView;

        public ContentViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.text_name);
            authorView = itemView.findViewById(R.id.text_author);
            imgView = itemView.findViewById(R.id.list_image);
            priceView = itemView.findViewById(R.id.text_price);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView headerTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            headerTextView = itemView.findViewById(R.id.text_list_header);
        }
    }
}