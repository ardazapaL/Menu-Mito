package com.example.menumito.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menumito.R;
import com.example.menumito.fragment.MainMenuFragment;
import com.example.menumito.model.OrderModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SingleMenuActivity extends AppCompatActivity {

    private static final String TAG = "SingleMenuActivity";
    private ImageView food_img;
    private TextView food_name, food_count;
    private MaterialButton btn_add, btn_min, btn_order;

    private int order_count;
    private ArrayList<OrderModel> orderModels;

    private FirebaseFirestore db;
    private CollectionReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_menu);

        db = FirebaseFirestore.getInstance();
        reference = db.collection("foods");

        food_img = findViewById(R.id.single_menu_image);
        food_name = findViewById(R.id.single_menu_name);
        food_count = findViewById(R.id.single_menu_count);
        btn_add = findViewById(R.id.single_menu_plus_btn);
        btn_min = findViewById(R.id.single_menu_min_btn);
        btn_order = findViewById(R.id.single_menu_order_btn);

        String id_food = getIntent().getStringExtra("food_id");
        Log.i(TAG, "ID Food ==> " + id_food);
        reference.document(id_food).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                Picasso.get().load(doc.getString("url")).into(food_img);
                food_name.setText(doc.getString("name"));
            } else {
                Log.e(TAG, "Error ==> " + task.getException());
            }
        });
        /* ORDER COUNTER */
        order_count = 1;
        food_count.setText(String.valueOf(order_count));

        btn_min.setOnClickListener(v -> {
            if (order_count == 1){
                Toast.makeText(this, "Pesananmu sudah minimum", Toast.LENGTH_SHORT).show();
            } else {
                order_count -= 1;
                food_count.setText(String.valueOf(order_count));
            }
        });

        /* BUTTON INCREMENT */
        btn_add.setOnClickListener(v -> {
            order_count += 1;
            food_count.setText(String.valueOf(order_count));
        });

        /* BUTTON ORDER CLICK LISTENER */
        btn_order.setOnClickListener(v -> {

            Intent intent = new Intent(SingleMenuActivity.this, HomeActivity.class);
            intent.putExtra("order" // NAME EXTRA INTENT
                    , new OrderModel(id_food
                    , String.valueOf(order_count)
                    , "tes"));
            startActivity(intent);
        });
    }
}