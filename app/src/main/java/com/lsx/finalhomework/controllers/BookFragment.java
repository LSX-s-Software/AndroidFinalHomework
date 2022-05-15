package com.lsx.finalhomework.controllers;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lsx.finalhomework.adapters.MyBookRecyclerViewAdapter;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Book;
import com.lsx.finalhomework.entities.BookService;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BookFragment extends Fragment implements View.OnClickListener, MyBookRecyclerViewAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    LinearLayout pagerView;

    BookService bs;
    List<Book> bookList;
    MyBookRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bs = new BookService(getContext());
        bookList = bs.getList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment_item_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.book_list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new MyBookRecyclerViewAdapter(bookList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // Set the pager
        pagerView = (LinearLayout) view.findViewById(R.id.linear_pager);
        pagerView.removeAllViews();
        View v = inflater.inflate(R.layout.pager_item, pagerView, false);
        v.setTag(0);
        TextView pagerTextView = (TextView) v.findViewById(R.id.pager_text);
        CardView pagerBG = (CardView) v.findViewById(R.id.pager_bg);
        pagerBG.setCardBackgroundColor(getResources().getColor(R.color.pager_bg_active, null));
        pagerTextView.setText("全部");
        v.setOnClickListener(this);
        pagerView.addView(v);
        for (Book.Category c : Book.Category.values()) {
            v = inflater.inflate(R.layout.pager_item, pagerView, false);
            v.setTag(c.ordinal() + 1);
            pagerTextView = (TextView) v.findViewById(R.id.pager_text);
            pagerTextView.setText(c.getName());
            v.setOnClickListener(this);
            pagerView.addView(v);
        }

        return view;
    }

    public void onItemClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        Bundle bundle = new Bundle();
        bundle.putInt("id", bookList.get(position).getId());
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.action_navigation_booklist_to_bookDetailFragment, bundle);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        // 处理分类切换事件
        if (position > 0) {
            List<Book> newList = new ArrayList<>();
            for (Book b : bookList)
                if (b.getCategory().ordinal() == position - 1)
                    newList.add(b);
            adapter.setData(newList);
        } else {
            adapter.setData(bookList);
        }
        for (int i = 0; i < pagerView.getChildCount(); i++) {
            CardView v1 = (CardView) pagerView.getChildAt(i);
            if (i == position)
                v1.setCardBackgroundColor(getResources().getColor(R.color.pager_bg_active, null));
            else
                v1.setCardBackgroundColor(getResources().getColor(R.color.pager_bg_inactive, null));
        }
    }
}