package com.example.deliverynest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliverynest.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NO_ORDERS_FOUND = 0;
    private static final int VIEW_TYPE_RECENT_ORDER_DESIGN = 1;

    private Context context;
    private ArrayList<RecentOrders> list;

    public MyAdapter(Context context, ArrayList<RecentOrders> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0) {
            // Return a different view type for "no orders found" layout
            return VIEW_TYPE_NO_ORDERS_FOUND;
        } else {
            // Return the default view type for "recent order design" layout
            return VIEW_TYPE_RECENT_ORDER_DESIGN;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NO_ORDERS_FOUND) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_order_layout, parent, false);
            return new NoOrdersFoundViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_order_design, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            RecentOrders recentOrders = list.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.orderid.setText(recentOrders.getOrder_id());
            myViewHolder.status.setText(recentOrders.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 1; // Display the "no orders found" layout
        } else {
            return list.size();
        }
    }

    public void updateData(ArrayList<RecentOrders> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderid, status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.Order_id);
            status = itemView.findViewById(R.id.status);
        }
    }

    private static class NoOrdersFoundViewHolder extends RecyclerView.ViewHolder {
        public NoOrdersFoundViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
