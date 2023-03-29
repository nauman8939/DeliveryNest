package com.example.deliverynest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OrderSummary extends BaseActivity {
    Button button;
    String Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        button = findViewById(R.id.checkout);
        Bundle bundle=getIntent().getExtras();
        String SenderName=bundle.getString("SenderName");
        String SenderPhone=bundle.getString("SenderPhone");
        String PickUpAddress=bundle.getString("PickUpAddress");
        String ReceiverName=bundle.getString("ReceiverName");
        String ReceiverPhone=bundle.getString("ReceiverPhone");
        String ReceiverAddress=bundle.getString("ReceiverAddress");
        String ItemNameToSend=bundle.getString("ItemNameToSend");
        String ParcelValue=bundle.getString("ParcelValue");
        String SenderLandmark=(String)bundle.get("SenderLandmark");
        String ReceiverLandmark=(String)bundle.get("ReceiverLandmark");
        String OrderWeightPO=(String)bundle.get("OrderWeightPO");
        String BookOption=(String)bundle.get("BookOption");
        String OrderWeight=(String)bundle.get("OrderWeight");
        String PreferBagOption=(String)bundle.get("PreferBagOption");
        String NotifyPersonOption=(String)bundle.get("NotifyPersonOption");


        EditText e1=findViewById(R.id.senders_name);
        EditText e2=findViewById(R.id.senders_address);
        EditText e3=findViewById(R.id.senders_contact);
        EditText e4=findViewById(R.id.senders_landmark);

        EditText e5=findViewById(R.id.receivers_name);
        EditText e6=findViewById(R.id.receivers_address);
        EditText e7=findViewById(R.id.receivers_contact);
        EditText e8=findViewById(R.id.receivers_landmark);




        e1.setText(OrderWeightPO);         e5.setText(ReceiverName);
        e2.setText(PickUpAddress);      e6.setText(ReceiverAddress);
        e3.setText(SenderPhone);        e7.setText(ReceiverPhone);
        e4.setText(SenderLandmark);     e8.setText(ReceiverLandmark);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Payment_Method.class);
                startActivity(intent);
            }
        });
    }
}