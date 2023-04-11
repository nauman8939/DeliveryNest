package com.example.deliverynest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewComplaint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        Bundle bundle=getIntent().getExtras();
        String subject=(String)bundle.get("ComplaintSubject");
        String status=(String)bundle.get("ComplaintStatus");
        String cdate=(String)bundle.get("ComplaintDate");
        String resolution=(String)bundle.get("ComplaintResolution");
        String description=(String)bundle.get("ComplaintDescription");

        if(status=="Incomplete" || status.equals("Incomplete")){
            resolution="We are trying to solve your complaint . As soon as it gets solved we will update you";
        }
        ((TextView)findViewById(R.id.status)).append(status);
        ((TextView)findViewById(R.id.subject)).append(subject);
        ((TextView)findViewById(R.id.date)).append(cdate);
        ((TextView)findViewById(R.id.resolution)).append(resolution);
        ((TextView)findViewById(R.id.description)).append(description);
    }
    public void backpressed(View view) {
        super.onBackPressed();
    }
}