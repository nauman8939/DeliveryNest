package com.example.deliverynest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class OrdersAdapter extends ArrayAdapter<Order_POJO> {
    ArrayList<Order_POJO> arrayList;
    public OrdersAdapter(@NonNull Context context, int resource, ArrayList<Order_POJO> Objects) {
        super(context, resource, resource, Objects);
        arrayList=Objects;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        View v= View.inflate(getContext(),R.layout.order_history_layout,null);
        TextView t1=v.findViewById(R.id.OrderId);
        TextView t2=v.findViewById(R.id.ItemNameToSend);
        TextView t3=v.findViewById(R.id.OrderDate);
        TextView t4=v.findViewById(R.id.OrderStatus);
        ImageView i1=v.findViewById(R.id.StatusSymbol);

        t1.append(arrayList.get(position).getOrderid());
        t2.setText(arrayList.get(position).getItemnametosend());
        t3.setText(arrayList.get(position).getOrderdate());
        t4.setText(arrayList.get(position).getStatus());
        i1.setImageResource(arrayList.get(position).getSymbol());
        return v;
    }
}
