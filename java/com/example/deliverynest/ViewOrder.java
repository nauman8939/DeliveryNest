package com.example.deliverynest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ViewOrder extends BaseActivity {
    private LoadingDialog aLodingDialog;
    Button TrackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        aLodingDialog = new LoadingDialog(ViewOrder.this);
        Bundle bundle=getIntent().getExtras();
        String Order_ID = bundle.get("OrderId").toString();
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

        Button btn=findViewById(R.id.CancelButton);

        Button btn1=findViewById(R.id.TrackBtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Track_Order.class);
                intent.putExtra("Order_ID",Order_ID);
                startActivity(intent);
            }
        });
        if(bundle.get("Status").toString()=="Cancelled" || bundle.get("Status").toString().equals("Cancelled") || bundle.get("Status").toString()=="Received" || bundle.get("Status").toString().equals("Received")){
            btn.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelOrder(bundle.get("OrderId").toString());
            }
        });
    }

    public void CancelOrder(String oid){
        View view=getLayoutInflater().inflate(R.layout.cancel_order_alert,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Cancel Order");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Orders").child(oid);
                Map map=new HashMap();
                map.put("Status","Cancelled");
                reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ViewOrder.this, "Order Cancelled ", Toast.LENGTH_SHORT).show();
                        loader();
                        Intent intent=new Intent(getApplicationContext(),All_Orders.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewOrder.this, "Error ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    private void loader() {
        aLodingDialog.show();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                aLodingDialog.cancel();
            }
        };
        handler.postDelayed(runnable, 3000);
    }
    public void backpressed(View view) {
        super.onBackPressed();
    }
}