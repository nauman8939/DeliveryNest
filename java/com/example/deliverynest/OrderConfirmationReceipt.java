package com.example.deliverynest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OrderConfirmationReceipt extends AppCompatActivity {
    TextView orderID;
    TextView PaymentMethod;
    TextView TDate;
    TextView Mode,Amt;
    Button HomeBtn;
    String Status;
    String SenderName, SenderPhone, PickUpAddress, ReceiverName, ReceiverPhone, ReceiverAddress, ItemNameToSend, ParcelValue, SenderLandmark,Assigned_to,
            ReceiverLandmark, BookOption, PreferBagOption, NotifyPersonOption, OrderWeight, OrderID,currentDate;
    int bookindex, spinnerPosition;
    int price;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation_receipt);
        orderID = findViewById(R.id.orderID);
        PaymentMethod = findViewById(R.id.Payment_method);
        TDate = findViewById(R.id.date);
        Mode = findViewById(R.id.Mode);
        Amt = findViewById(R.id.Amt);
        HomeBtn = findViewById(R.id.Home_btn);

        Bundle bundle = getIntent().getExtras();

        BookOption = (String) bundle.get("BookOption");
        bookindex = (int) bundle.get("bookindex");

        spinnerPosition = getIntent().getIntExtra("spinner_position", 0);
        OrderWeight = getIntent().getStringExtra("OrderWeight");


        OrderID = bundle.getString("OrderID");
        price = (int) bundle.getInt("Price", 0);
        currentDate = bundle.getString("currentDate");
        Status="Received";
        Assigned_to="In Process";
        orderID.setText(OrderID);
        Mode.setText(BookOption);

        TDate.setText(currentDate);
        Amt.setText(String.valueOf(price));

        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(intent);
            }
        });

    }
  
}