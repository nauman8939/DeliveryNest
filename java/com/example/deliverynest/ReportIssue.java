package com.example.deliverynest;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

public class ReportIssue extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";
    private final StorageReference reference= FirebaseStorage.getInstance().getReference().child("complaints");
    Spinner spinner;
    EditText editText;
    Button submitbtn, uploadbtn;
    ImageView img;
    SharedPreferences sharedpreferences;
    String[] issuecategory = {"Choose a subject", "Order related", "Payment related", "Agent related","Report a bug"};
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, issuecategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,false);
        editText = findViewById(R.id.issueDesc);
        submitbtn=findViewById(R.id.BtnSubmit);
        uploadbtn = findViewById(R.id.BtnUpload);
        img=findViewById(R.id.ImagePreview);
        SessionManager sessionManager = new SessionManager(this);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateIssueDesc();
                validateSpinner();
                String dropdownOption = spinner.getSelectedItem().toString();
                String issueDesc = editText.getText().toString();
                HashMap<String, String> usersDetails = sessionManager.getUsersDetailsFromSession();
                String username = usersDetails.get(SessionManager.KEY_USERNAME);

                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("complaints");
                HashMap map = new HashMap();
                map.put("complaint_subject", dropdownOption);
                map.put("complaint_desc", issueDesc);
                if (imgUri !=null) {
                    ContentResolver cr=getContentResolver();
                    MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
                    StorageReference filref=reference.child(System.currentTimeMillis()+"."+mimeTypeMap.getExtensionFromMimeType(cr.getType(imgUri)));
                    filref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            int id=new Random().nextInt(10000);
                            map.put("complaint_id",id);
                            map.put("username", username);
                            db.child(""+id).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ReportIssue.this, "Complaint Registered", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),ComplaintSection.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ReportIssue.this, "Error : Failed to Register", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),ComplaintSection.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ReportIssue.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent clickImage = new Intent();
                    clickImage.setAction(Intent.ACTION_GET_CONTENT);
                    clickImage.setType("image/*");
                    startActivityForResult(clickImage,2);
                }
            });
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2){
                imgUri=data.getData();
                img.setImageURI(imgUri);
            }
        }
        public boolean validateIssueDesc(){
            String issuedesc = editText.getText().toString();
            if (issuedesc.isEmpty()){
                editText.setError("Field cannot be empty");
                return false;
            }else{
                editText.setError(null);
                editText.setEnabled(false);
                return true;
            }
        }
        public boolean validateSpinner(){
            String spinnerData = spinner.getAdapter().toString();
            if (spinner.getSelectedItemPosition()==0){
                Toast.makeText(this, "Choose a Subject", Toast.LENGTH_LONG).show();
                return false;
            }else if(spinner.getSelectedItemPosition()>0){
                return true;
            }else{
                return true;
            }
        }
        public void backpressed(View view) {
            super.onBackPressed();

        }
    }