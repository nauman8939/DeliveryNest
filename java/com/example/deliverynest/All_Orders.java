package com.example.deliverynest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class All_Orders extends AppCompatActivity {
    SessionManager sessionManager;
    OrdersAdapter customAdapter;
    String orderid,assignedto,bookoption,itemnametosend,loggedusername,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,
    preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price;
    int symbol;
    String user;
    DatabaseReference reference;
    ArrayList <Order_POJO>arrayList = new ArrayList();
    LottieAnimationView animationView;
    ImageView i1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        ListView l1 = findViewById(R.id.OrderView);

        reference = FirebaseDatabase.getInstance().getReference().child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                        ShowOrders(snapshot1.getKey(), l1);
                                                    }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
    }
    public void ShowOrders(String orderId, ListView l1){
        orderid=orderId;
        sessionManager = new SessionManager(this);
        animationView = findViewById(R.id.notFoundAnimation);
        HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
        loggedusername = usersDetails.get(SessionManager.KEY_USERNAME);
        reference = FirebaseDatabase.getInstance().getReference().child("Orders").child(orderid);
        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                for (DataSnapshot datasnapshot : snapshot.getChildren())
                                {
                                    if (datasnapshot.getKey().equals("Assigned_to"))
                                    {
                                        assignedto = datasnapshot.getValue().toString();
                                    }
                                    else if (datasnapshot.getKey().equals("Status"))
                                    {
                                        status = datasnapshot.getValue().toString();
                                        if (status == "Received" || status.equals("Received")) {
                                            symbol = R.drawable.solved;
                                        }
                                        else
                                        {
                                            symbol = R.drawable.incomplete;
                                        }
                                    }
                                    else if (datasnapshot.getKey().equals("BookOption")) {
                                        bookoption = datasnapshot.getValue().toString();
                                    }
                                    else if (datasnapshot.getKey().equals("LoggedUsername")) {
                                        user=datasnapshot.getValue().toString();
                                        if(user==loggedusername || user.equals(loggedusername)){
                                            l1.setVisibility(View.VISIBLE);
                                            animationView.setVisibility(View.GONE);
                                        }
                                        else{
                                            l1.setVisibility(View.GONE);
                                            animationView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    else if (datasnapshot.getKey().equals("ItemNameToSend")) {
                                        itemnametosend=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("NotifyPersonOption")){
                                        notifypersonoption=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("OrderDate")){
                                        orderdate=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("OrderWeight")){
                                        orderweight=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("ParcelValue")){
                                        parcelvalue=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("PickUpAddress")){
                                        pickupaddress=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("PreferBagOption")){
                                        preferbagoption=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("ReceiverAddress")){
                                        receiveraddress=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("ReceiverLandmark")){
                                        receiverlandmark=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("ReceiverName")){
                                        receivername=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("ReceiverPhone")){
                                        receiverphone=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("SenderLandmark")){
                                        senderlandmark=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("SenderName")){
                                        sendername=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("price")){
                                        price=datasnapshot.getValue().toString();
                                    }
                                    else if(datasnapshot.getKey().equals("SenderPhone")){
                                        senderphone=datasnapshot.getValue().toString();
                                    }
                                    else{

                                    }
                                    orderid=orderId;
                                }
                                if(user== loggedusername || user.equals(loggedusername)) {
                                    arrayList.add(new Order_POJO(symbol,orderid,assignedto,bookoption,itemnametosend,notifypersonoption,orderdate,orderweight,parcelvalue,pickupaddress,preferbagoption,receiveraddress,receiverlandmark,receivername,receiverphone,senderlandmark,sendername,senderphone,status,price));
                                }
                                customAdapter= new OrdersAdapter(getApplicationContext(), R.layout.order_history_layout,arrayList);
                                l1.setAdapter(customAdapter);
                                l1.setOnItemClickListener((parent, view, position, id) -> {
                                    Intent intent=new Intent(getApplicationContext(),ViewOrder.class);
                                    intent.putExtra("OrderId",arrayList.get(position).getOrderid());
                                    intent.putExtra("Status",arrayList.get(position).getStatus());
                                    intent.putExtra("AssignedTo",arrayList.get(position).getAssignedto());
                                    intent.putExtra("BookOption",arrayList.get(position).getBookoption());
                                    intent.putExtra("ItemNameToSend",arrayList.get(position).getItemnametosend());
                                    intent.putExtra("NotifyPersonOption",arrayList.get(position).getNotifypersonoption());
                                    intent.putExtra("OrderDate",arrayList.get(position).getOrderdate());
                                    intent.putExtra("OrderWeight",arrayList.get(position).getOrderweight());
                                    intent.putExtra("ParcelValue",arrayList.get(position).getParcelvalue());
                                    intent.putExtra("PickUpAddress",arrayList.get(position).getPickupaddress());
                                    intent.putExtra("PreferBagOption",arrayList.get(position).getPreferbagoption());
                                    intent.putExtra("ReceiverAddress",arrayList.get(position).getReceiveraddress());
                                    intent.putExtra("ReceiverLandmark",arrayList.get(position).getReceiverlandmark());
                                    intent.putExtra("ReceiverName",arrayList.get(position).getReceivername());
                                    intent.putExtra("ReceiverPhone",arrayList.get(position).getReceiverphone());
                                    intent.putExtra("SenderLandmark",arrayList.get(position).getSenderlandmark());
                                    intent.putExtra("SenderName",arrayList.get(position).getSendername());
                                    intent.putExtra("SenderPhone",arrayList.get(position).getSenderphone());
                                    intent.putExtra("Price",arrayList.get(position).getPrice());
                                    intent.putExtra("Symbol",arrayList.get(position).getSymbol());
                                    startActivity(intent);
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
    }
    public void backpressed(View view) {
        super.onBackPressed();
    }

}