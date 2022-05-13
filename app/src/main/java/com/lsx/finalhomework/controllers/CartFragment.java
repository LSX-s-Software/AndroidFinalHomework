package com.lsx.finalhomework.controllers;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.lsx.finalhomework.MyAuth;
import com.lsx.finalhomework.adapters.MyCartRecyclerViewAdapter;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Book;
import com.lsx.finalhomework.entities.BookService;
import com.lsx.finalhomework.entities.Cart;
import com.lsx.finalhomework.entities.CartItem;
import com.lsx.finalhomework.placeholder.PlaceholderContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CartFragment extends Fragment implements MyCartRecyclerViewAdapter.OnItemClickListener {

    RecyclerView recyclerView;

    Cart cart;
    List<CartItem> cartItemList;

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
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyCartRecyclerViewAdapter adapter = new MyCartRecyclerViewAdapter(cartItemList);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public int onItemQuantityChange(int position, int quantity) {
        if (cartItemList.get(position).getQuantity() + quantity > 0) {
            cart.addToCart(cartItemList.get(position).getBookId(), quantity);
            cartItemList = cart.getCart();
            return cartItemList.get(position).getQuantity();
        } else {
            cart.removeFromCart(cartItemList.get(position).getBookId());
            cartItemList = cart.getCart();
            return 0;
        }
    }

    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", cartItemList.get(position).getBookId());
        NavController navController = Navigation.findNavController(recyclerView);
        navController.navigate(R.id.action_navigation_cart_to_bookDetailFragment, bundle);
    }
}