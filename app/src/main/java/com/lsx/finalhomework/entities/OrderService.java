package com.lsx.finalhomework.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lsx.finalhomework.MyAuth;
import com.lsx.finalhomework.MyDBHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService extends MyDBHelper {

    int accountId;

    public OrderService(Context context, int accountId) {
        super(context);
        this.accountId = accountId;
    }

    public void addOrder(Order order) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account_id", order.getAccountId());
        values.put("order_time", order.getOrderTime().format(Order.dateTimeFormatter));
        long orderId = db.insertOrThrow("book_order", null, values);
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            ContentValues orderDetailValues = new ContentValues();
            orderDetailValues.put("order_id", orderId);
            orderDetailValues.put("book_id", orderDetail.getBookId());
            orderDetailValues.put("order_price", orderDetail.getOrderPrice());
            orderDetailValues.put("quantity", orderDetail.getQuantity());
            db.insertOrThrow("order_detail", null, orderDetailValues);
        }
        db.close();
    }

    public void deleteOrder(int orderId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("order_detail", "order_id=?", new String[]{String.valueOf(orderId)});
        db.delete("book_order", "id=?", new String[]{String.valueOf(orderId)});
        db.close();
    }

    public List<Order> getOrderList() {
        List<Order> orderList = new ArrayList<>();
        Map<Integer, Integer> orderMap = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from book_order,order_detail,book " +
                "where account_id=? AND book_order.id=order_detail.order_id AND book.id=order_detail.book_id", new String[]{String.valueOf(accountId)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            Order order;
            if (orderMap.containsKey(id)) {
                order = orderList.get(orderMap.get(id));
            } else {
                order = new Order();
                order.setId(id);
                order.setAccountId(cursor.getInt((int) cursor.getColumnIndex("account_id")));
                order.setOrderTime(LocalDateTime.parse(cursor.getString((int) cursor.getColumnIndex("order_time")), Order.dateTimeFormatter));
                List<OrderDetail> orderDetailList = new ArrayList<>();
                order.setOrderDetails(orderDetailList);
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(cursor.getInt(3));
            orderDetail.setOrderId(cursor.getInt((int) cursor.getColumnIndex("order_id")));
            orderDetail.setBookId(cursor.getInt((int) cursor.getColumnIndex("book_id")));
            orderDetail.setBook(BookService.deserializer(cursor, 8));
            orderDetail.setOrderPrice(cursor.getDouble((int) cursor.getColumnIndex("order_price")));
            orderDetail.setQuantity(cursor.getInt((int) cursor.getColumnIndex("quantity")));
            order.getOrderDetails().add(orderDetail);
            if (!orderMap.containsKey(id)) {
                orderList.add(order);
                orderMap.put(order.getId(), orderList.size() - 1);
            }
        }
        cursor.close();
        db.close();
        return orderList;
    }

    public Order getOrder(int orderId) {
        Order order = new Order();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("book_order", null, "id=?", new String[]{String.valueOf(orderId)}, null, null, null);
        if (cursor.moveToNext()) {
            order.setId(cursor.getInt((int) cursor.getColumnIndex("id")));
            order.setAccountId(cursor.getInt((int) cursor.getColumnIndex("account_id")));
            order.setOrderTime(LocalDateTime.parse(cursor.getString((int) cursor.getColumnIndex("order_time")), Order.dateTimeFormatter));
            order.setOrderDetails(new ArrayList<>());
            cursor = db.rawQuery("select * from order_detail,book " +
                    "where order_detail.order_id=? AND book.id=order_detail.book_id", new String[]{String.valueOf(orderId)});
            while (cursor.moveToNext()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(cursor.getInt(0));
                orderDetail.setOrderId(cursor.getInt((int) cursor.getColumnIndex("order_id")));
                orderDetail.setBookId(cursor.getInt((int) cursor.getColumnIndex("book_id")));
                orderDetail.setBook(BookService.deserializer(cursor, 5));
                orderDetail.setOrderPrice(cursor.getDouble((int) cursor.getColumnIndex("order_price")));
                orderDetail.setQuantity(cursor.getInt((int) cursor.getColumnIndex("quantity")));
                order.getOrderDetails().add(orderDetail);
            }
        }
        cursor.close();
        db.close();
        return order;
    }

    public void createOrder(List<CartItem> cart) {
        Order newOrder = new Order();
        newOrder.setAccountId(accountId);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cart) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBookId(cartItem.getBook().getId());
            orderDetail.setOrderPrice(cartItem.getBook().getPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetails.add(orderDetail);
        }
        newOrder.setOrderDetails(orderDetails);
        newOrder.setOrderTime(LocalDateTime.now());
        addOrder(newOrder);
    }
}
