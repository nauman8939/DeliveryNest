package com.example.deliverynest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class PaymentPage extends BaseActivity {
    String SenderName, SenderPhone, PickUpAddress, ReceiverName, ReceiverPhone, ReceiverAddress, ItemNameToSend, ParcelValue, SenderLandmark,
            ReceiverLandmark, BookOption, PreferBagOption, NotifyPersonOption, OrderWeight;
    int bookindex, spinnerPosition;
    int price;
    Button proceedButton;
    TextView subtotal3,subtotal2,subtotal1,Totalamt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        RadioGroup paymentgroup = findViewById(R.id.paymentgroup);
        subtotal3 = findViewById(R.id.subtotal3);
        subtotal2 = findViewById(R.id.subtotal2);
        subtotal1 = findViewById(R.id.subtotal);
        Totalamt = findViewById(R.id.totalamt);
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

        int subtotal = price/2;

        subtotal3.setText(String.valueOf(price));
        subtotal2.setText(String.valueOf(subtotal));
        subtotal1.setText(String.valueOf(subtotal));
        Totalamt.setText(String.valueOf(price));

        // get the current date and year
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) % 100; // get last two digits of the year
        int month = calendar.get(Calendar.MONTH) + 1; // add 1 to month since it's zero-indexed

        // generate a random two-digit number
        Random random = new Random();
        int randomNumber = random.nextInt(90) + 10; // random number between 10 and 99

        // create the updated string
        String OrderID = String.format("DN%d%02d%02d%d", year, month, calendar.get(Calendar.DATE), randomNumber);

        proceedButton = (Button)findViewById(R.id.checkoutbutton);


        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPaymentOptionId = paymentgroup.getCheckedRadioButtonId();

                if (selectedPaymentOptionId == R.id.cod) {
                    // COD option is selected, start COD activity
                    Intent intent = new Intent(getApplicationContext(), COD.class);
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
                    intent.putExtra("NotifyPersonOption", NotifyPersonOption);
                    startActivity(intent);
                } else if (selectedPaymentOptionId == R.id.paytm) {
                    // Paytm option is selected, start Paytm activity
                    Intent intent = new Intent(getApplicationContext(), COD.class);
                    // add any necessary extra data to the intent
                    startActivity(intent);
                } else {
                    // no payment option is selected, display an error message or do nothing
                }
            }
        });

    }


}