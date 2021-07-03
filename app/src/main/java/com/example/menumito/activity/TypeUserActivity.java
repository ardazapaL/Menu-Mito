package com.example.menumito.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.menumito.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TypeUserActivity extends AppCompatActivity {

    private static final String TAG = "TypeUserActivity";
    private LinearLayout typeUserKitchen, typeUserWaiter;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private Map<String, Object> newProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_user);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        typeUserKitchen = findViewById(R.id.type_user_kitchen);
        typeUserWaiter = findViewById(R.id.type_user_waiter);

        if (auth.getCurrentUser() != null) {
            String phone = auth.getCurrentUser().getPhoneNumber();
            Log.i(TAG, "Logged in as " + phone);

            newProfile = new HashMap<>();
            newProfile.put("phone", phone);
            newProfile.put("type", "");

            db.collection("user").document(user.getUid())
                    .set(newProfile).addOnSuccessListener(aVoid -> {
                Log.i(TAG, "Success login");
            });
        }
        typeUserKitchen.setOnClickListener(v -> {

            db.collection("user").document(user.getUid())
                    .update("type", "kitchen")
                    .addOnSuccessListener(aVoid -> {

                        Log.i(TAG, "Type User updated");
                        startActivity(new Intent(TypeUserActivity.this,
                                HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    });
        });

        typeUserWaiter.setOnClickListener(v -> {

            db.collection("user").document(user.getUid())
                    .update("type", "waiter")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.i(TAG, "Type User updated");
                            startActivity(new Intent(TypeUserActivity.this,
                                    HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                    });
        });

    }
}