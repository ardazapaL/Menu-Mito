package com.example.menumito.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.menumito.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    private String mVerificationId;

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Button btnVerify, btnResend;
    private TextView descPhone;
    private PinView pinViewCode;

    private static final String TAG = "Verify Activity";
    private String phoneNumber;
    private String inputCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnVerify = findViewById(R.id.btn_verify);
        pinViewCode = findViewById(R.id.pinView_verify);
        descPhone = findViewById(R.id.text_current_phone);
        btnResend = findViewById(R.id.btn_resend);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        phoneNumber = extra.getString("phone");
        mVerificationId = extra.getString("id");

        descPhone.setText("Code send to " + phoneNumber);
        inputCode = pinViewCode.getText().toString();

        btnVerify.setOnClickListener(v -> verifyWithCode());

        btnResend.setOnClickListener(v -> {

            resendVerificationCode(phoneNumber, mForceResendingToken);
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Log.e(TAG, "Error " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);

                Log.i(TAG, "onCodeSent " + verificationId);

                mVerificationId = verificationId;
                mForceResendingToken = forceResendingToken;
            }
        };
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .setForceResendingToken(token)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {

                    String phone = auth.getCurrentUser().getPhoneNumber();
                    Log.i(TAG, "Logged in as " + phone);

                    startActivity(new Intent(VerifyActivity.this, SendNotif.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                })
                .addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }

    private void verifyWithCode() {

        if (inputCode.isEmpty()) {

            Toast.makeText(this, "Please fill the code", Toast.LENGTH_SHORT);
            return;
        } else {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, inputCode);
            Log.i(TAG,"Verification ID ==> " + mVerificationId);
            Log.i(TAG, "Input code ==> " + inputCode);

            signInWithPhoneAuthCredential(credential);
        }
    }
}