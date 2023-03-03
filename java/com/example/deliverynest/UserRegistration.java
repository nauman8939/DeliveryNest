package com.example.deliverynest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRegistration extends AppCompatActivity {
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private LoadingDialog aLodingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        aLodingDialog = new LoadingDialog(UserRegistration.this);
        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        //Save data in FireBase on button click

    }//onCreate Method End

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void callLogin(View view) {
        Intent intent2 = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent2);
    }
    public void register(View view ){
        if (!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword()| !saveUserData()) {
            return;
        }
        else {
            loader();

            passdata();
        }
    }
    private void passdata(){
        Intent i = new Intent(UserRegistration.this,VerifyOTP.class);
        final String name = regName.getEditText().getText().toString();
        final String username = regUsername.getEditText().getText().toString();
        final String email = regEmail.getEditText().getText().toString();
        final String phoneNo = regPhoneNo.getEditText().getText().toString();
        final String password = regPassword.getEditText().getText().toString();

        String MobileNumber ="+91"+phoneNo;

        i.putExtra("name",name);
        i.putExtra("MobileNumber",MobileNumber);
        i.putExtra("username",username);
        i.putExtra("email",email);
        i.putExtra("phoneNo",phoneNo);
        i.putExtra("password",password);
        i.putExtra("whatToDO", "createNewUser");
        startActivity(i);

    }
    private boolean saveUserData() {



            final String username = regUsername.getEditText().getText().toString();

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(username).exists()) {
                        regUsername.setError("Username already exists");
                        regUsername.requestFocus();

                    }
                    else {

                        passdata();

                    }
                    }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return false;
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


}