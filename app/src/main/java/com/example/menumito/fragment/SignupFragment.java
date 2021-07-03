package com.example.menumito.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menumito.R;
import com.example.menumito.activity.HomeActivity;
import com.example.menumito.activity.TypeUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignupFragment extends Fragment {

    private static final String TAG = "SignupFragment";
    private String mVerificationId;

    private EditText editTextPhone, editTextCode;
    private Intent goSignup;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        /* FIREBASE */
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        /* VARIABLE */
        editTextPhone = view.findViewById(R.id.input_phone);
        editTextCode = view.findViewById(R.id.input_password);
        Button btnLogin = view.findViewById(R.id.btn_signup);
        Button btnSubmit = view.findViewById(R.id.btn_submit);

        /* INTENT */
        goSignup = new Intent(getActivity(), TypeUserActivity.class);
        goSignup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

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
                //mForceResendingToken = forceResendingToken;
            }
        };

        /* BUTTON LOGIN LISTENER */
        btnLogin.setOnClickListener(v -> {
            String phoneNumber = editTextPhone.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                editTextPhone.setError(getString(R.string.empty_field));
            } else {
                startPhoneNumberVerification(phoneNumber);
                //startActivity(new Intent(getActivity(), SendNotif.class));
                //.putExtra("phone", phoneNumber)
                //.putExtra("id", mVerificationId));
            }
        });
        // BUTTON SUBMIT CODE LISTENER
        btnSubmit.setOnClickListener(v -> {
            String code = editTextCode.getText().toString().trim();

            if (code.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter the code..", Toast.LENGTH_SHORT).show();
            } else {
                verifyWithCode(mVerificationId, code);
            }
        });
        return view;
    }
    /* SEND CODE VERIFICATION */
    private void startPhoneNumberVerification(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    /* VERIFY CODE */
    private void verifyWithCode(String verificationId, String code) {
        if (code.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the code", Toast.LENGTH_SHORT).show();
            editTextCode.requestFocus();
        } else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            Log.i(TAG,"Verification ID ==> " + verificationId);
            Log.i(TAG, "Input code ==> " + code);

            signInWithPhoneAuthCredential(credential);
        }
    }
    /* LOGIN WITH PHONE CREDENTIAL */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                        startActivity(goSignup);
            });
    }
}
