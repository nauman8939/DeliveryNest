package com.example.deliverynest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OrderSummary extends BaseActivity {
    private LoadingDialog aLodingDialog;
    Button button;
    String SenderName,SenderPhone,PickUpAddress,ReceiverName,ReceiverPhone,ReceiverAddress,ItemNameToSend,ParcelValue,SenderLandmark,
            ReceiverLandmark,BookOption,PreferBagOption,NotifyPersonOption,OrderWeight;
    int bookindex,spinnerPosition;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        button = findViewById(R.id.checkout);
        Bundle bundle=getIntent().getExtras();
         SenderName=bundle.getString("SenderName");
         SenderPhone=bundle.getString("SenderPhone");
         PickUpAddress=bundle.getString("PickUpAddress");
         ReceiverName=bundle.getString("ReceiverName");
         ReceiverPhone=bundle.getString("ReceiverPhone");
         ReceiverAddress=bundle.getString("ReceiverAddress");
         ItemNameToSend=bundle.getString("ItemNameToSend");
         ParcelValue=bundle.getString("ParcelValue");
         SenderLandmark=(String)bundle.get("SenderLandmark");
         ReceiverLandmark=(String)bundle.get("ReceiverLandmark");
         BookOption=(String)bundle.get("BookOption");
         bookindex=(int)bundle.get("bookindex");
         PreferBagOption=(String)bundle.get("PreferBagOption");
         NotifyPersonOption=(String)bundle.get("NotifyPersonOption");
         spinnerPosition = getIntent().getIntExtra("spinner_position",0);
         OrderWeight = getIntent().getStringExtra("OrderWeight");



        if (bookindex == 0) {
            if (spinnerPosition == 0) {
                price = 100;
            }
            else if (spinnerPosition == 1) {
                price = 150;
            } else if (spinnerPosition == 2) {
                price = 200;
            } else if (spinnerPosition == 3) {
                price = 250;
            } else {
                price = 0; // Default price if spinner1 position is not valid
            }
        } else if (bookindex == 1) {
            price = 500;
        } else if (bookindex == 2) {
            price = 1000;
        } else {
            price = 0; // Default price if spinner position is not valid
        }







        price = Integer.parseInt(String.valueOf(price));
        EditText e1=findViewById(R.id.senders_name);
        EditText e2=findViewById(R.id.senders_address);
        EditText e3=findViewById(R.id.senders_contact);
        EditText e4=findViewById(R.id.senders_landmark);

        EditText e5=findViewById(R.id.receivers_name);
        EditText e6=findViewById(R.id.receivers_address);
        EditText e7=findViewById(R.id.receivers_contact);
        EditText e8=findViewById(R.id.receivers_landmark);




        e1.setText(SenderName);         e5.setText(ReceiverName);
        e2.setText(PickUpAddress);      e6.setText(ReceiverAddress);
        e3.setText(SenderPhone);        e7.setText(ReceiverPhone);
        e4.setText(SenderLandmark);     e8.setText(ReceiverLandmark);


    }

    public void checkout(View view){

        Intent intent = new Intent(getApplicationContext(),PaymentPage.class);
        intent.putExtra("SenderName",SenderName);
        intent.putExtra("SenderPhone",SenderPhone);
        intent.putExtra("PickUpAddress",PickUpAddress);
        intent.putExtra("ReceiverName",ReceiverName);
        intent.putExtra("ReceiverPhone",ReceiverPhone);
        intent.putExtra("ReceiverAddress",ReceiverAddress);
        intent.putExtra("ItemNameToSend",ItemNameToSend);
        intent.putExtra("ParcelValue",ParcelValue);
        intent.putExtra("SenderLandmark",SenderLandmark);
        intent.putExtra("ReceiverLandmark",ReceiverLandmark);
        intent.putExtra("BookOption",BookOption);
        intent.putExtra("bookindex", bookindex);
        intent.putExtra("Price", price);
        intent.putExtra("spinner_position", spinnerPosition);
        intent.putExtra("OrderWeight", OrderWeight);
        intent.putExtra("PreferBagOption",PreferBagOption);
        intent.putExtra("NotifyPersonOption",NotifyPersonOption);


        startActivity(intent);
    }

}