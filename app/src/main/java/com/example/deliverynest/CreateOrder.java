package com.example.deliverynest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateOrder extends AppCompatActivity  {
    Button BtnDocuments,BtnFood,BtnGroceries,BtnCloth;
    EditText Item;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        BtnDocuments=(Button) findViewById(R.id.Documents);
        BtnFood=(Button) findViewById(R.id.Food);
        BtnGroceries=(Button) findViewById(R.id.Groceries);
        BtnCloth=(Button) findViewById(R.id.Cloth);
        Item=(EditText)findViewById(R.id.itemNameToSend);

        BtnDocuments.setOnClickListener(v -> {
            Item.setText("Documents");
        });
        BtnFood.setOnClickListener(v -> {
            Item.setText("Food");
        });
        BtnGroceries.setOnClickListener(v -> {
            Item.setText("Groceries");
        });
        BtnCloth.setOnClickListener(v -> {
            Item.setText("Cloth");
        });
    }
}