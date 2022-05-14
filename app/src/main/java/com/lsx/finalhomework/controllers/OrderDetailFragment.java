package com.lsx.finalhomework.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lsx.finalhomework.MyAuth;
import com.lsx.finalhomework.NWImageView;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Order;
import com.lsx.finalhomework.entities.OrderDetail;
import com.lsx.finalhomework.entities.OrderService;

import java.util.List;

public class OrderDetailFragment extends Fragment implements View.OnClickListener {

    int orderId, position;
    OrderService orderService;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        orderId = getArguments().getInt("id");
        position = getArguments().getInt("position");
        orderService = new OrderService(getContext(), MyAuth.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);

        // 订单信息
        TextView idView = view.findViewById(R.id.text_order_id2);
        TextView countView = view.findViewById(R.id.text_order_count2);
        TextView priceView = view.findViewById(R.id.text_order_price2);
        TextView timeView = view.findViewById(R.id.text_order_time2);

        Order order = orderService.getOrder(orderId);
        idView.setText(String.format("%s", order.getId()));
        List<OrderDetail> orderDetails = order.getOrderDetails();
        double price = 0;
        for (OrderDetail orderDetail : orderDetails)
            price += orderDetail.getOrderPrice() * orderDetail.getQuantity();
        countView.setText(String.format("%s", orderDetails.size()));
        priceView.setText(String.format("%.2f", price));
        timeView.setText(order.getOrderTime().format(Order.dateTimeFormatter));

        // 订单书本列表
        LinearLayout orderDetailView = view.findViewById(R.id.linear_order_detail);
        for (OrderDetail orderDetail : orderDetails) {
            View itemView = inflater.inflate(R.layout.book_fragment_item, orderDetailView, false);
            TextView nameView = itemView.findViewById(R.id.text_name);
            TextView authorView = itemView.findViewById(R.id.text_author);
            NWImageView imgView = itemView.findViewById(R.id.list_image);
            TextView priceView2 = itemView.findViewById(R.id.text_price);
            nameView.setText(orderDetail.getBook().getName());
            authorView.setText(orderDetail.getBook().getAuthor());
            priceView2.setTextSize(14);
            priceView2.setText(String.format("%sx\n%.2f", orderDetail.getQuantity(), orderDetail.getOrderPrice()));
            imgView.setImageURL(orderDetail.getBook().getImgUrl());
            orderDetailView.addView(itemView);
        }

        // 订单操作
        Button delBtn = view.findViewById(R.id.btn_del_order);
        delBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_del_order) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.app_name)
                    .setMessage("是否确定删除此订单？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        orderService.deleteOrder(orderId);
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        OrderFragment.instance.refresh(position);
                        Navigation.findNavController(v).navigateUp();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {}).create()
                    .show();
        }
    }
}