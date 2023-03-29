package com.example.deliverynest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends BaseActivity {
    EditText e1,e2,e3,e4;
    LottieAnimationView lottie;
    TextView t1;
    Button editProfileButton,updateProfileButton,cancelProfileButton,logoutButton;
    LinearLayout l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        l1=findViewById(R.id.bottomLayout);
        l1.setVisibility(View.GONE);
        e1=findViewById(R.id.email);
        e2=findViewById(R.id.fullName);
        e3=findViewById(R.id.phone);
        e4=findViewById(R.id.username);
        t1=findViewById(R.id.fullNameHeading);
        editProfileButton=findViewById(R.id.editProfileButton);
        cancelProfileButton=findViewById(R.id.cancelProfileButton);
        updateProfileButton=findViewById(R.id.updateProfileButton);
        logoutButton=findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.VISIBLE);
                editProfileButton.setVisibility(View.GONE);
            }
        });

        cancelProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.GONE);
                editProfileButton.setVisibility(View.VISIBLE);
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map=new HashMap();
                map.put("name",e2.getText().toString());
                map.put("phoneNo",e3.getText().toString());
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child("tejas8102001");
                databaseReference.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ProfilePage.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        l1.setVisibility(View.GONE);
                        editProfileButton.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfilePage.this, "Unable to update Profile", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        getRecords();
    }
    public void getRecords(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child("tejas8102001");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot: snapshot.getChildren()) {
                    if(datasnapshot.getKey().equals("email")){
                        e1.setText(datasnapshot.getValue().toString());
                    }
                    else if(datasnapshot.getKey().equals("name")){
                        e2.setText(datasnapshot.getValue().toString());
                        t1.setText(datasnapshot.getValue().toString());
                    }
                    else if(datasnapshot.getKey().equals("phoneNo")){
                       e3.setText(datasnapshot.getValue().toString());
                    }
                    else{
                        e4.setText(datasnapshot.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}