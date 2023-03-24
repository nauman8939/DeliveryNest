package com.example.deliverynest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Iterator;

public class CreateOrder extends AppCompatActivity  {
    Button BtnDocuments,BtnFood,BtnGroceries,BtnCloth,NextButton;
    EditText Item,SenderName,SenderPhone,PickUpAddress,ReceiverName,ReceiverPhone,ReceiverAddress,ItemNameToSend,ParcelValue;
    Spinner BookOption,OrderWeight,SenderLandmark,ReceiverLandmark;
    Switch PreferBagOption,NotifyPersonOption;
    Boolean choice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        BtnDocuments= findViewById(R.id.Documents);
        BtnFood= findViewById(R.id.Food);
        BtnGroceries= findViewById(R.id.Groceries);
        BtnCloth= findViewById(R.id.Cloth);
        Item= findViewById(R.id.ItemNameToSend);

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

        SenderName=findViewById(R.id.SenderName);
        SenderPhone=findViewById(R.id.SenderPhone);
        PickUpAddress=findViewById(R.id.PickUpAddress);
        ReceiverName=findViewById(R.id.ReceiverName);
        ReceiverPhone=findViewById(R.id.ReceiverPhone);
        ReceiverAddress=findViewById(R.id.ReceiverAddress);
        ItemNameToSend=findViewById(R.id.ItemNameToSend);
        ParcelValue=findViewById(R.id.ParcelValue);

        BookOption=findViewById(R.id.BookOption);
        OrderWeight=findViewById(R.id.OrderWeight);
        SenderLandmark=findViewById(R.id.SenderLandmark);
        ReceiverLandmark=findViewById(R.id.ReceiverLandmark);

        PreferBagOption=findViewById(R.id.PreferBagOption);
        NotifyPersonOption=findViewById(R.id.NotifyPersonOption);

        NextButton=findViewById(R.id.NextButton);

        EditText[] EditTextArr ={SenderName,SenderPhone,PickUpAddress,ReceiverName,ReceiverPhone,ReceiverAddress,ItemNameToSend,ParcelValue};
        Spinner[] spinner ={SenderLandmark,ReceiverLandmark};
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator iterator= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    iterator = Arrays.stream(EditTextArr).iterator();
                }
                while(iterator.hasNext()){
                    Object obj=iterator.next();
                    choice=CheckEditTextField((EditText)obj);
                }
                Iterator iterator1= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    iterator1 = Arrays.stream(spinner).iterator();
                }
                while(iterator1.hasNext()){
                    Object obj=iterator1.next();
                    choice=CheckSpinnerField((Spinner)obj);
                }
                if(SenderName.getText()==ReceiverName.getText()){
                    SenderName.setError("Sender and Receiver Can't be same ");
                    ReceiverName.setError("Sender and Receiver Can't be same ");
                    choice=false;
                }
                if(SenderPhone.getText()==ReceiverPhone.getText()){
                    SenderPhone.setError("Sender and Receiver Phone Can't be same ");
                    ReceiverPhone.setError("Sender and Receiver Phone Can't be same ");
                    choice=false;
                }
                if(PickUpAddress.getText()==ReceiverAddress.getText()){
                    PickUpAddress.setError("Sender and Receiver Address Can't be same ");
                    ReceiverAddress.setError("Sender and Receiver Address Can't be same ");
                    choice=false;
                }
                if(choice){
                    Intent intent=new Intent(getApplicationContext(),OrderSummary.class);
                    intent.putExtra("SenderName",SenderName.getText().toString());
                    intent.putExtra("SenderPhone",SenderPhone.getText().toString());
                    intent.putExtra("PickUpAddress",PickUpAddress.getText().toString());
                    intent.putExtra("ReceiverName",ReceiverName.getText().toString());
                    intent.putExtra("ReceiverPhone",ReceiverPhone.getText().toString());
                    intent.putExtra("ReceiverAddress",ReceiverAddress.getText().toString());
                    intent.putExtra("ItemNameToSend",ItemNameToSend.getText().toString());
                    intent.putExtra("ParcelValue",ParcelValue.getText().toString());
                    intent.putExtra("SenderLandmark",SenderLandmark.getSelectedItem().toString());
                    intent.putExtra("ReceiverLandmark",ReceiverLandmark.getSelectedItem().toString());
                    intent.putExtra("PreferBagOption",PreferBagOption.getText().toString());
                    intent.putExtra("NotifyPersonOption",NotifyPersonOption.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
    public boolean CheckEditTextField(EditText FieldNameReceived){
        if(FieldNameReceived.getText().toString().isEmpty()){
            FieldNameReceived.setError("Field Cannot be Empty");
            return false;
        }
        else{
            FieldNameReceived.setError(null);
            return true;
        }
    }
    public boolean CheckSpinnerField(Spinner FieldNameReceived){
        if(FieldNameReceived.getSelectedItem()==FieldNameReceived.getItemAtPosition(0)){
            TextView errorText = (TextView)FieldNameReceived.getSelectedView();
            errorText.setError("Choose atleast One Value");
            return false;
        }
        else{
            TextView errorText = (TextView)FieldNameReceived.getSelectedView();
            errorText.setError(null);
            return true;
        }
    }
}