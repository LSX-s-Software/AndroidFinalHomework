package com.lsx.finalhomework.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsx.finalhomework.NWImageView;
import com.lsx.finalhomework.R;
import com.lsx.finalhomework.entities.CartItem;

import java.util.List;

public class MyCartRecyclerViewAdapter extends RecyclerView.Adapter<MyCartRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
        int onItemQuantityChange(int position, int quantity);
    }

    private final List<CartItem> cartItemList;

    MyCartRecyclerViewAdapter.OnItemClickListener mItemClickListener;

    public MyCartRecyclerViewAdapter(List<CartItem> items) {
        cartItemList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.nameTextView.setText(item.getBook().getName());
        holder.priceTextView.setText(String.format("¥%s", item.getBook().getPrice()));
        holder.countTextView.setText(String.valueOf(item.getQuantity()));
        holder.imageView.setImageURL(item.getBook().getImgUrl());
        // 监听项目点击事件
        holder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(holder.getAbsoluteAdapterPosition()));
        // 监听减号按钮点击事件
        holder.btnMinus.setOnClickListener(v -> {
            int adapterPosition = holder.getAbsoluteAdapterPosition();
            // 调用CartFragment的onItemQuantityChange方法处理书本数量变化事件
            int newQ = mItemClickListener.onItemQuantityChange(adapterPosition, -1);
            item.setQuantity(newQ);
            holder.countTextView.setText(String.valueOf(newQ));
            if (newQ == 0) {
                // 从列表中移除该项
                cartItemList.remove(item);
                // 通知列表更新
                notifyItemRemoved(adapterPosition);
            }
        });
        // 监听加号按钮点击事件
        holder.btnPlus.setOnClickListener(v -> {
            int adapterPosition = holder.getAbsoluteAdapterPosition();
            // 调用CartFragment的onItemQuantityChange方法处理书本数量变化事件
            int newQ = mItemClickListener.onItemQuantityChange(adapterPosition, 1);
            item.setQuantity(newQ);
            holder.countTextView.setText(String.valueOf(newQ));
        });
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final NWImageView imageView;
        public final TextView nameTextView;
        public final TextView priceTextView;
        public final TextView countTextView;
        public final TextView btnPlus;
        public final TextView btnMinus;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.list_image2);
            nameTextView = view.findViewById(R.id.text_name2);
            priceTextView = view.findViewById(R.id.text_price2);
            countTextView = view.findViewById(R.id.text_quantity);
            btnPlus = view.findViewById(R.id.btn_plus);
            btnMinus = view.findViewById(R.id.btn_minus);
        }
    }
}