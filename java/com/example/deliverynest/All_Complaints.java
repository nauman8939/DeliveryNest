package com.example.deliverynest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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


public class All_Complaints extends AppCompatActivity {
    SessionManager sessionManager;
    String subject,status,Cdate,resolution,description;
    String user="";
    int symbol;
    String username;
    TextView t1,t2,t3;
    DatabaseReference reference;
    ArrayList <Complaint_POJO>arrayList = new ArrayList();
    LottieAnimationView animationView;
    String[] FinalString =new String[50];
    int j=0;
    int k=0;
    ImageView i1;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_all_complaints);

            sessionManager = new SessionManager(this);
            animationView = findViewById(R.id.notFoundAnimation);
            HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
            username = usersDetails.get(SessionManager.KEY_USERNAME);

            reference = FirebaseDatabase.getInstance().getReference().child("complaints");
            reference.addValueEventListener(new ValueEventListener() {
                final String arr = "";

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        FinalString[j] = snapshot1.getKey();
                        j++;
                    }
                        ListView l1 = findViewById(R.id.ComplaintView);
                            for (int i = 0; i < j; i++)
                            {
                                            reference = FirebaseDatabase.getInstance().getReference().child("complaints").child(FinalString[i]);
                                            reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot)
                                        {
                                                for (DataSnapshot datasnapshot : snapshot.getChildren())
                                                {
                                                    if (datasnapshot.getKey().equals("username")) {
                                                        user=datasnapshot.getValue().toString();
                                                        if(user.equals(username) || user==username){
                                                            l1.setVisibility(View.VISIBLE);
                                                            animationView.setVisibility(View.GONE);
                                                        }
                                                        else{
                                                            animationView.setVisibility(View.VISIBLE);
                                                            l1.setVisibility(View.GONE);
                                                        }
                                                    }
                                                    else if (datasnapshot.getKey().equals("complaint_subject"))
                                                    {
                                                        subject = datasnapshot.getValue().toString();
                                                    }
                                                    else if (datasnapshot.getKey().equals("complaint_status"))
                                                    {
                                                        status = datasnapshot.getValue().toString();
                                                        if (status == "Solved" || status.equals("Solved")) {
                                                                symbol = R.drawable.solved;
                                                        }
                                                        else
                                                        {
                                                                symbol = R.drawable.incomplete;
                                                        }
                                                    }
                                                    else if (datasnapshot.getKey().equals("complaintDate")) {
                                                        Cdate = datasnapshot.getValue().toString();
                                                    }
                                                    else if(datasnapshot.getKey().equals("complaint_resolution")){
                                                        resolution=datasnapshot.getValue().toString();
                                                    }
                                                    else if(datasnapshot.getKey().equals("complaint_desc")){
                                                        description=datasnapshot.getValue().toString();
                                                    }
                                                }
                                                if(user==username || user.equals(username)) {
                                                    arrayList.add(new Complaint_POJO(subject, status, Cdate, symbol,description,resolution));
                                                }
                                            ComplaintsAdapter customAdapter = new ComplaintsAdapter(getApplicationContext(), R.layout.complaint_layout, arrayList);
                                            l1.setAdapter(customAdapter);
                                            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent intent=new Intent(getApplicationContext(),ViewComplaint.class);
                                                    intent.putExtra("ComplaintSubject",arrayList.get(position).getSubject());
                                                    intent.putExtra("ComplaintStatus",arrayList.get(position).getStatus());
                                                    intent.putExtra("ComplaintDate",arrayList.get(position).getDate());
                                                    intent.putExtra("ComplaintResolution",arrayList.get(position).getResolution());
                                                    intent.putExtra("ComplaintDescription",arrayList.get(position).getDescription());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                         @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
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