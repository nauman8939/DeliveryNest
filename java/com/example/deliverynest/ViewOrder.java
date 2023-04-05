package com.example.deliverynest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Bundle bundle=getIntent().getExtras();

        ((TextView)findViewById(R.id.OrderId)).append(bundle.get("OrderId").toString());
        ((TextView)findViewById(R.id.Status)).append(bundle.get("Status").toString());
        ((TextView)findViewById(R.id.AssignedTo)).append(bundle.get("AssignedTo").toString());
        ((TextView)findViewById(R.id.BookOption)).append(bundle.get("BookOption").toString());
        ((TextView)findViewById(R.id.ItemNameToSend)).append(bundle.get("ItemNameToSend").toString());
        ((TextView)findViewById(R.id.NotifyBySmsOption)).append(bundle.get("NotifyPersonOption").toString());
        ((TextView)findViewById(R.id.OrderDate)).append(bundle.get("OrderDate").toString());
        ((TextView)findViewById(R.id.OrderWeight)).append(bundle.get("OrderWeight").toString());
        ((TextView)findViewById(R.id.SenderAddress)).append(bundle.get("PickUpAddress").toString());
        ((TextView)findViewById(R.id.BagPrefered)).append(bundle.get("PreferBagOption").toString());
        ((TextView)findViewById(R.id.ReceiverAddress)).append(bundle.get("ReceiverAddress").toString());
        ((TextView)findViewById(R.id.ReceiverName)).append(bundle.get("ReceiverName").toString());
        ((TextView)findViewById(R.id.ReceiverContact)).append(bundle.get("ReceiverPhone").toString());
        ((TextView)findViewById(R.id.SenderName)).append(bundle.get("SenderName").toString());
        ((TextView)findViewById(R.id.SenderContact)).append(bundle.get("SenderPhone").toString());
        ((TextView)findViewById(R.id.Price)).setText("Rs."+bundle.get("Price").toString()+"/-");
    }
    public void backpressed(View view) {
        super.onBackPressed();
    }
}