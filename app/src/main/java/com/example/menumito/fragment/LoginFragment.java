package com.example.menumito.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menumito.R;

import com.example.menumito.activity.HomeActivity;
import com.example.menumito.activity.SendNotif;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPass;
    private Button btnLogin;
    private Intent goLogin;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        /* firebase */
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /* variable */
        editTextEmail = view.findViewById(R.id.input_email);
        editTextPass = view.findViewById(R.id.input_password);
        btnLogin = view.findViewById(R.id.btn_login);

        /* intent login */
        goLogin = new Intent(getActivity(), SendNotif.class);
        goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();
            }
        });
        return view;
    }

    /* method login */
    private void Login() {

        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        /* if email text is empty */
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.empty_field));
            return;
        }
        /* if pass text is empty */
        if (password.isEmpty()) {
            editTextPass.setError(getString(R.string.empty_field));
            return;
        }

        auth.signInWithEmailAndPassword(email.trim(), password.trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user = auth.getCurrentUser();
                        startActivity(goLogin);
                        Log.d("LoginFragment", user.getUid());
                    }
                });
    }
}
