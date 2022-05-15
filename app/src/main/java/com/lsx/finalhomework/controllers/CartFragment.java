package com.lsx.finalhomework.controllers;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lsx.finalhomework.MyAuth;
import com.lsx.finalhomework.adapters.MyCartRecyclerViewAdapter;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Book;
import com.lsx.finalhomework.entities.BookService;
import com.lsx.finalhomework.entities.Cart;
import com.lsx.finalhomework.entities.CartItem;
import com.lsx.finalhomework.entities.OrderService;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CartFragment extends Fragment implements View.OnClickListener, MyCartRecyclerViewAdapter.OnItemClickListener {

    RecyclerView recyclerView;

    Cart cart;
    List<CartItem> cartItemList;
    TextView totalPriceView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cart = new Cart(getContext(), MyAuth.getUserId());
        cartItemList = cart.getCart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment_item_list, container, false);

        // Set the adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.list_cart);
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyCartRecyclerViewAdapter adapter = new MyCartRecyclerViewAdapter(cartItemList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        totalPriceView = view.findViewById(R.id.text_totalprice);
        Button orderBtn = view.findViewById(R.id.btn_order);
        orderBtn.setOnClickListener(this);

        updateTotalPriceView();

        return view;
    }

    private void updateTotalPriceView() {
        if (cartItemList.size() > 0) {
            totalPriceView.setText(String.format("¥%.2f", cart.getTotalPrice()));
        } else {
            totalPriceView.setText("¥0.00");
        }
    }

    public int onItemQuantityChange(int position, int quantity) {
        if (cartItemList.get(position).getQuantity() + quantity > 0) {
            cart.addToCart(cartItemList.get(position).getBookId(), quantity);
            cartItemList = cart.getCart();
            updateTotalPriceView();
            return cartItemList.get(position).getQuantity();
        } else {
            cart.removeFromCart(cartItemList.get(position).getBookId());
            cartItemList = cart.getCart();
            updateTotalPriceView();
            return 0;
        }
    }

    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", cartItemList.get(position).getBookId());
        NavController navController = Navigation.findNavController(recyclerView);
        navController.navigate(R.id.action_navigation_cart_to_bookDetailFragment, bundle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_order) {
            if (cartItemList.size() > 0) {
                OrderService orderService = new OrderService(getContext(), MyAuth.getUserId());
                orderService.createOrder(cart.getCart());
                cart.clearCart();
                updateTotalPriceView();
                BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
                navBar.removeBadge(R.id.navigation_cart);
                recyclerView.getAdapter().notifyItemRangeRemoved(0, cartItemList.size());
                Toast.makeText(getContext(), "购买成功", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_navigation_cart_to_navigation_order);
            } else {
                Toast.makeText(getContext(), "购物车为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}