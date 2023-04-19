package com.example.deliverynest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComplaintSection extends BaseActivity {

    TextView textView4,textView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_section);

        textView4 = findViewById(R.id.textView4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintSection.this, ReportIssue.class);
                startActivityForResult(intent, 1);
                intent.putExtra("Result", 2);
            }
        });

        textView5=findViewById(R.id.textView5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComplaintSection.this, All_Complaints.class);
                startActivity(intent);
            }
        });
    }
    public void backpressed(View view) {
        super.onBackPressed();
    }
}