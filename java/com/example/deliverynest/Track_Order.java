package com.example.deliverynest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Track_Order extends BaseActivity {

    EditText orderidinput;
    Button Track_btn,Home_btn;
    TextView EstimateDateText,OrderNumberText,Statusoforder,AssignedTo,orderidinputText;
    String Status,OrderDate,AssignedToPartner,OrderID;
    String PhoneNo;
    LinearLayout trackifempty,trackorder;
    String Order_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        orderidinputText = findViewById(R.id.orderidinput);
        System.out.println(orderidinput);
        Track_btn = findViewById(R.id.Track_btn);
        EstimateDateText = findViewById(R.id.EstimateDate);
        OrderNumberText = findViewById(R.id.OrderNumber);
        trackifempty = findViewById(R.id.trackifempty);
        trackorder = findViewById(R.id.trackorder);
        AssignedTo = findViewById(R.id.AssignedTo);
        Statusoforder = findViewById(R.id.Statusoforder);
        trackifempty.setVisibility(View.VISIBLE);
        trackorder.setVisibility(View.GONE);
        OrderID= orderidinputText.getText().toString();
        Home_btn = findViewById(R.id.Home_btn);
        PhoneNo = "7038230674";
        Order_ID = getIntent().getStringExtra("Order_ID");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders");
        System.out.println(OrderID);
        Track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(orderidinputText.getWindowToken(), 0);

                OrderID= orderidinputText.getText().toString();
           //     Toast.makeText(Track_Order.this, ""+OrderID, Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders").child(OrderID);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            if (snapshot1.getKey().equals("Status")){
                                 Status = snapshot1.getValue().toString();
                            } else if (snapshot1.getKey().equals("OrderDate")) {
                                OrderDate = snapshot1.getValue().toString();
                            }else if (snapshot1.getKey().equals("Assigned_to")) {
                                AssignedToPartner = snapshot1.getValue().toString();
                            }
                        }
                        trackifempty.setVisibility(View.GONE);
                        trackorder.setVisibility(View.VISIBLE);
                        Statusoforder.setText(Status);
                        AssignedTo.setText(AssignedToPartner);
                        EstimateDateText.setText(OrderDate);
                        OrderNumberText.setText(OrderID);
                        System.out.println(OrderDate);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

        Home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + PhoneNo));
                startActivity(intent);
            }
        });

    }
    public  void Cancel(View v){
        Intent intent = new Intent(getApplicationContext(),UserDashboard.class);
        startActivity(intent);
    }
}