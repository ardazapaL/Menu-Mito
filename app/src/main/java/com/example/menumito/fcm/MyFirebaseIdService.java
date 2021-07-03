package com.example.menumito.fcm;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseIdService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIdService";
    private FirebaseUser firebaseUser;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                String refreshToken = task.getResult();
                Log.i("Token ==> ", refreshToken);

                if (firebaseUser != null){

                    updateToken(refreshToken);
                }
            }
        });
    }

    private void updateToken(String refreshToken){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Token token1 = new Token(refreshToken);
        Map<String, Object> newToken = new HashMap<>();
        newToken.put("token", token1);

        db.collection("user").document(firebaseUser.getUid())
                .set(newToken).addOnCompleteListener(task -> {
                    Log.i(TAG, "Success updated token");
                });
    }
}