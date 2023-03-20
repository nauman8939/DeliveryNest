package com.example.deliverynest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_SMS_RECEIVE = 1;
    PinView pinFromUser;
    String codeBySystem;
    TextView otpDescriptionText;
    private FirebaseAuth mAuth;
    String name, username, email, phoneNo, password, whatToDO,date;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private LoadingDialog aLodingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        mAuth = FirebaseAuth.getInstance();

        pinFromUser = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_description_text);
        //getting data
        name = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        phoneNo = getIntent().getStringExtra("phoneNo");
        password = getIntent().getStringExtra("password");

        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date = format.format(todaysdate);

        String _phoneNo = getIntent().getStringExtra("MobileNumber");
        otpDescriptionText.setText("Enter One Time Password Sent On " + _phoneNo);
        sendVerificationCodeToUser(_phoneNo);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        aLodingDialog = new LoadingDialog(VerifyOTP.this);


    }

    private void sendVerificationCodeToUser(String phoneNo) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loader();
                            saveUserData();

                            // Sign in success, update UI with the signed-in user's information


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try Again", Toast.LENGTH_SHORT);

                            }
                        }
                    }
                });
    }

    public void callNextScreenFromOTP(View view) {
        String code = pinFromUser.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }

    private void saveUserData() {


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password,date);
                reference.child(username).setValue(helperClass);
                Toast.makeText(VerifyOTP.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerifyOTP.this, LoginScreen.class);
                startActivity(intent);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SMS_RECEIVE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now read and receive SMS
            } else {
                // Permission denied, you cannot read and receive SMS
            }
        }
    }
}