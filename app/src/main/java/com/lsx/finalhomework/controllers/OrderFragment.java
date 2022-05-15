package com.lsx.finalhomework.controllers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsx.finalhomework.MyAuth;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.adapters.MyOrderRecyclerViewAdapter;
import com.lsx.finalhomework.entities.Order;
import com.lsx.finalhomework.entities.OrderService;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class OrderFragment extends Fragment implements MyOrderRecyclerViewAdapter.OnItemClickListener {

    RecyclerView recyclerView;

    OrderService orderService;
    List<Order> orderList;

    static OrderFragment instance;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderService = new OrderService(getContext(), MyAuth.getUserId());
        orderList = orderService.getOrderList();
        instance = this;
    }

    public static OrderFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyOrderRecyclerViewAdapter adapter = new MyOrderRecyclerViewAdapter(orderList);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    public void onItemClick(int position) {
        Order order = orderList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", order.getId());
        bundle.putInt("position", position);
        NavController navController = Navigation.findNavController(recyclerView);
        navController.navigate(R.id.action_navigation_order_to_orderDetailFragment, bundle);
    }

    public void refresh(int position) {
        orderList.remove(position);
        MyOrderRecyclerViewAdapter adapter = (MyOrderRecyclerViewAdapter) recyclerView.getAdapter();
        assert adapter != null;
        adapter.notifyItemRemoved(position);
    }
}