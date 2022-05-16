package com.lsx.finalhomework.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.Order;
import com.lsx.finalhomework.entities.OrderDetail;

import java.util.List;

public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private List<Order> mValues;

    MyOrderRecyclerViewAdapter.OnItemClickListener mItemClickListener;

    public MyOrderRecyclerViewAdapter(List<Order> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Order order = mValues.get(position);
        holder.idView.setText(String.format("%s", order.getId()));
        List<OrderDetail> orderDetails = order.getOrderDetails();
        double price = 0;
        for (OrderDetail orderDetail : orderDetails)
            price += orderDetail.getOrderPrice() * orderDetail.getQuantity();
        holder.countView.setText(String.format("%s", orderDetails.size()));
        holder.priceView.setText(String.format("%.2f", price));
        holder.timeView.setText(order.getOrderTime().format(Order.dateTimeFormatter));
        holder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(holder.getAbsoluteAdapterPosition()));
    }

    public void setOnItemClickListener(MyOrderRecyclerViewAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView idView;
        public final TextView countView;
        public final TextView priceView;
        public final TextView timeView;

        public ViewHolder(View view) {
            super(view);
            idView = view.findViewById(R.id.text_order_id);
            countView = view.findViewById(R.id.text_order_count);
            priceView = view.findViewById(R.id.text_order_price);
            timeView = view.findViewById(R.id.text_order_time);
        }
    }
}