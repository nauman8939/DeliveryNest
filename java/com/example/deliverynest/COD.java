package com.example.deliverynest;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class COD extends BaseActivity {
    String SenderName, SenderPhone, PickUpAddress, ReceiverName, ReceiverPhone, ReceiverAddress, ItemNameToSend, ParcelValue, SenderLandmark,Status,Assigned_to,
            ReceiverLandmark, BookOption, PreferBagOption, NotifyPersonOption, OrderWeight, OrderID;
    int bookindex, spinnerPosition;
    int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod);
        Bundle bundle = getIntent().getExtras();
        SenderName = bundle.getString("SenderName");
        SenderPhone = bundle.getString("SenderPhone");
        PickUpAddress = bundle.getString("PickUpAddress");
        ReceiverName = bundle.getString("ReceiverName");
        ReceiverPhone = bundle.getString("ReceiverPhone");
        ReceiverAddress = bundle.getString("ReceiverAddress");
        ItemNameToSend = bundle.getString("ItemNameToSend");
        ParcelValue = bundle.getString("ParcelValue");
        SenderLandmark = (String) bundle.get("SenderLandmark");
        ReceiverLandmark = (String) bundle.get("ReceiverLandmark");
        BookOption = (String) bundle.get("BookOption");
        bookindex = (int) bundle.get("bookindex");
        PreferBagOption = (String) bundle.get("PreferBagOption");
        NotifyPersonOption = (String) bundle.get("NotifyPersonOption");
        spinnerPosition = getIntent().getIntExtra("spinner_position", 0);
        OrderWeight = getIntent().getStringExtra("OrderWeight");
        price = (int) bundle.getInt("Price", 0);
        OrderID = bundle.getString("OrderID");
        Status="Received";
        Assigned_to="In Process";



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("Orders");

// Get the current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

// Get the logged user's username from the session manager
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUsersDetailsFromSession();
        String UserName = user.get(SessionManager.KEY_USERNAME);

// Create a new node with the specified key and values
        Order order = new Order(Status,Assigned_to,SenderName, SenderPhone, PickUpAddress, ReceiverName, ReceiverPhone, ReceiverAddress, ItemNameToSend, ParcelValue, SenderLandmark, ReceiverLandmark, BookOption, PreferBagOption, NotifyPersonOption, OrderWeight, price, currentDate, UserName);
        ordersRef.child(OrderID).setValue(order);


        // Redirect after 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(COD.this, OrderConfirmationReceipt.class);

                intent.putExtra("OrderID", OrderID);
                intent.putExtra("SenderName", SenderName);
                intent.putExtra("SenderPhone", SenderPhone);
                intent.putExtra("PickUpAddress", PickUpAddress);
                intent.putExtra("ReceiverName", ReceiverName);
                intent.putExtra("ReceiverPhone", ReceiverPhone);
                intent.putExtra("ReceiverAddress", ReceiverAddress);
                intent.putExtra("ItemNameToSend", ItemNameToSend);
                intent.putExtra("ParcelValue", ParcelValue);
                intent.putExtra("SenderLandmark", SenderLandmark);
                intent.putExtra("ReceiverLandmark", ReceiverLandmark);
                intent.putExtra("BookOption", BookOption);
                intent.putExtra("bookindex", bookindex);
                intent.putExtra("Price", price);
                intent.putExtra("spinner_position", spinnerPosition);
                intent.putExtra("OrderWeight", OrderWeight);
                intent.putExtra("PreferBagOption", PreferBagOption);
                intent.putExtra("currentDate", currentDate);

                startActivity(intent);
                finish();
            }
        }, 3000); // 5000 milliseconds = 5 seconds

        Toast.makeText(getApplicationContext(), "Request Created Please Wait", Toast.LENGTH_SHORT).show();
    }


    }


