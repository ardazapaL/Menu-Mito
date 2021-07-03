package com.example.menumito.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menumito.R;
import com.example.menumito.adapter.CheckoutAdapter;
import com.example.menumito.fcm.APIService;
import com.example.menumito.fcm.Client;
import com.example.menumito.fcm.Data;
import com.example.menumito.fcm.MyResponse;
import com.example.menumito.fcm.NotificationSender;
import com.example.menumito.fcm.Token;
import com.example.menumito.model.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = "CheckoutActivity";
    private ArrayList<OrderModel> checkoutModels;

    private FirebaseFirestore db;
    private FirebaseUser user;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_checkout);
        ExtendedFloatingActionButton fab = findViewById(R.id.fab_checkout);
        apiService = Client.getClient("https://fcm.googleapis.com/")
                .create(APIService.class);

        /* GET LIST ORDER FROM HOME ACTIVITY */
        checkoutModels = HomeActivity.getArrayList();

        /* CHECK ARRAY FROM HOME ACTIVITY */
        for (int i = 0; i < checkoutModels.size(); i++) {
            OrderModel model = checkoutModels.get(i);
            Log.i(TAG, "List Order " + i + " ==> " + model.getId());
        }

        /*
        FAB CLICK LISTENER
        SUBMIT ORDER TO FIREBASE
        */
        fab.setOnClickListener(v -> {
            /* ADD ORDER TO FIREBASE */
            for (int i = 0; i < checkoutModels.size(); i++) {
                OrderModel model = checkoutModels.get(i);

                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("name_order", model.getId());
                orderMap.put("total_order", model.getOrder_count());

                db.collection("order").add(orderMap)
                        .addOnSuccessListener(documentReference -> Log.i(TAG, "Order added"));
            }
            /* NOTIF TO KITCHEN */
            db.collection("user").whereEqualTo("type", "waiter") // TARGET UID
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                sendNotifications(document.getString("token")
                                        ,"New Order"
                                        ,checkoutModels.size() + " New Order" );
                            }
                        }
                    });
            UpdateToken();
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CREATE CHANNEL TO SHOW NOTIF
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_id);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        /* ATTACH ADAPTER */
        CheckoutAdapter adapter = new CheckoutAdapter(checkoutModels);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void sendNotifications(String userToken, String title, String message) {

        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, userToken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(CheckoutActivity.this
                                ,"Failed to Send Notif"
                                ,Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

                Log.e(TAG, "Error ==> " + t.getMessage());
            }
        });
    }

    private void UpdateToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String refreshToken = task.getResult();
                        Log.i("TOKEN ==>", refreshToken);

                        Token token = new Token(refreshToken);
                        String sToken = token.getToken();

                        Map<String, Object> newToken = new HashMap<>();
                        newToken.put("token", sToken);
                        db.collection("user").document(user.getUid()).update(newToken);
                    }
                });
    }
}