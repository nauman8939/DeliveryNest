package com.example.deliverynest;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.webkit.MimeTypeMap;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.*;
import com.google.firebase.database.*;
import com.google.firebase.storage.*;
import java.util.*;

public class ReportIssue extends BaseActivity {
    private LoadingDialog aLodingDialog;
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
        aLodingDialog = new LoadingDialog(ReportIssue.this);
        spinner = findViewById(R.id.categorySpinner);
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
                long id=new Random().nextLong();

                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("complaints");
                HashMap map = new HashMap();
                map.put("complaint_subject", dropdownOption);
                map.put("complaint_desc", issueDesc);
                if (imgUri !=null) {
                    ContentResolver cr=getContentResolver();
                    MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
                    StorageReference filref=reference.child(id+"."+mimeTypeMap.getExtensionFromMimeType(cr.getType(imgUri)));
                    filref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            loader();

                            String date= null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                date = ""+java.time.LocalDate.now();
                            }
                            map.put("complaint_id",id);
                            map.put("complaintDate",date);
                            map.put("complaint_status","Incomplete");
                            map.put("complaint_resolution","");
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