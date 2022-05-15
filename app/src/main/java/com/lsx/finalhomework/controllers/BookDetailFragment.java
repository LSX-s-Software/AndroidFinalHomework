package com.lsx.finalhomework.controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lsx.finalhomework.MyAuth;
import com.lsx.finalhomework.NWImageView;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Book;
import com.lsx.finalhomework.entities.BookService;
import com.lsx.finalhomework.entities.Cart;

public class BookDetailFragment extends Fragment implements View.OnClickListener {

    int bookId;
    BookService bs;

    TextView nameView, authorView, isbnView, descView;
    NWImageView imageView;

    public BookDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        bookId = getArguments().getInt("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        nameView = view.findViewById(R.id.text_name2);
        authorView = view.findViewById(R.id.text_author2);
        isbnView = view.findViewById(R.id.text_isbn);
        descView = view.findViewById(R.id.text_desc);
        imageView = view.findViewById(R.id.detail_img);
        FloatingActionButton addToCart = view.findViewById(R.id.btn_addToCart);
        addToCart.setOnClickListener(this);

        bs = new BookService(getContext());
        Book b = bs.getBook(bookId);
        nameView.setText(b.getName());
        authorView.setText(b.getAuthor());
        isbnView.setText(String.format("ISBN: %s", b.getISBN()));
        descView.setText(b.getDescription());
        imageView.setImageURL(b.getImgUrl());

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addToCart) {
            Cart cart = new Cart(getContext(), MyAuth.getUserId());
            cart.addToCart(bookId, 1);
            Toast.makeText(getContext(), "已加入购物车", Toast.LENGTH_SHORT).show();
            BottomNavigationView navBar  = getActivity().findViewById(R.id.nav_view);
            navBar.getOrCreateBadge(R.id.navigation_cart).setNumber(cart.getCartCount());
        }
    }
}