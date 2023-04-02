package com.example.deliverynest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ComplaintsAdapter extends ArrayAdapter {
    ArrayList<Complaint_POJO> arrayList;
    public ComplaintsAdapter(@NonNull Context context, int resource, ArrayList<Complaint_POJO>Objects) {
        super(context, resource, Objects);
        arrayList=Objects;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        view=View.inflate(getContext(),R.layout.complaint_layout,null);
        TextView t1=view.findViewById(R.id.subject);
        TextView t2=view.findViewById(R.id.status);
        TextView t3=view.findViewById(R.id.date);
        ImageView i1=view.findViewById(R.id.UpdateSymbol);
        t1.setText(arrayList.get(position).getSubject());
        t2.setText(arrayList.get(position).getStatus());
        t3.setText(arrayList.get(position).getDate());
        i1.setImageResource(arrayList.get(position).getSymbol());
        return view;
    }
}
