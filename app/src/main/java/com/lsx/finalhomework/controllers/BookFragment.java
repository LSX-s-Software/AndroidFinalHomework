package com.lsx.finalhomework.controllers;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lsx.finalhomework.adapters.MyBookRecyclerViewAdapter;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Book;
import com.lsx.finalhomework.entities.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BookFragment extends Fragment implements View.OnClickListener, MyBookRecyclerViewAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    LinearLayout pagerView;
    GridLayoutManager gridLayoutManager;

    BookService bs;
    ArrayList bookListWithHeader;
    List<Book.Category> categoryList;
    HashMap<Integer, Integer> headerPositionMap;
    MyBookRecyclerViewAdapter adapter;
    int currentPagerIndex = 0;

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
        // 分类
        List<Book> bookList = bs.getList();
        HashMap<Book.Category, List<Book>> map = new HashMap<>();
        categoryList = new ArrayList<>();
        headerPositionMap = new HashMap<>();
        for (int i = 0; i < bookList.size(); i++) {
            if (!map.containsKey(bookList.get(i).getCategory())) {
                Book.Category c = bookList.get(i).getCategory();
                map.put(c, new ArrayList<>());
                categoryList.add(c);
            }
            map.get(bookList.get(i).getCategory()).add(bookList.get(i));
        }
        bookListWithHeader = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            Book.Category c = categoryList.get(i);
            headerPositionMap.put(bookListWithHeader.size(), i);
            bookListWithHeader.add(c);
            bookListWithHeader.addAll(map.get(c));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment_item_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.book_list);
        gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (bookListWithHeader.get(position) instanceof Book.Category) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyBookRecyclerViewAdapter(bookListWithHeader);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //根据索引来获取对应的itemView
                int position = gridLayoutManager.findFirstVisibleItemPosition();
                if (headerPositionMap.containsKey(position))
                    setActivePager(headerPositionMap.get(position));
            }
        });

        // Set the pager
        pagerView = (LinearLayout) view.findViewById(R.id.linear_pager);
        pagerView.removeAllViews();
        for (int i = 0; i < categoryList.size(); i++) {
            Book.Category c = categoryList.get(i);
            CardView v = (CardView) inflater.inflate(R.layout.pager_item, pagerView, false);
            v.setTag(c.ordinal() + 1);
            TextView pagerTextView = (TextView) v.findViewById(R.id.pager_text);
            pagerTextView.setText(c.getName());
            v.setOnClickListener(this);
            if (i == 0)
                v.setCardBackgroundColor(getResources().getColor(R.color.pager_bg_active, null));
            pagerView.addView(v);
        }

        return view;
    }

    public void onItemClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        Bundle bundle = new Bundle();
        Object item = bookListWithHeader.get(position);
        if (item instanceof Book) {
            bundle.putInt("id", ((Book) item).getId());
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_navigation_booklist_to_bookDetailFragment, bundle);
        }
    }

    @Override
    public void onClick(View v) {
        // 处理分类切换事件
        int position = pagerView.indexOfChild(v);
        int category = (int) v.getTag() - 1;
        int headerPosition = 0;
        if (position > 0) {
            for (int i = 0; i < bookListWithHeader.size(); i++) {
                if (bookListWithHeader.get(i) == Book.Category.values()[category]) {
                    headerPosition = i;
                    break;
                }
            }
            gridLayoutManager.scrollToPositionWithOffset(headerPosition, 0);
        } else {
            gridLayoutManager.scrollToPositionWithOffset(0, 0);
        }
        setActivePager(position);
    }

    private void setActivePager(int position) {
        if (currentPagerIndex == position)
            return;
        for (int i = 0; i < pagerView.getChildCount(); i++) {
            CardView v1 = (CardView) pagerView.getChildAt(i);
            if (i == position)
                v1.setCardBackgroundColor(getResources().getColor(R.color.pager_bg_active, null));
            else
                v1.setCardBackgroundColor(getResources().getColor(R.color.pager_bg_inactive, null));
        }
        currentPagerIndex = position;
    }
}