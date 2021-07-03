package com.example.menumito.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menumito.R;
import com.example.menumito.model.OrderModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class SingleMenuActivity extends AppCompatActivity {

    private static final String TAG = "SingleMenuActivity";
    private ImageView food_img;
    private TextView food_name, food_count;

    private int order_count;
    private String url_food_img, txt_name_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_menu);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("foods");

        food_img = findViewById(R.id.single_menu_image);
        food_name = findViewById(R.id.single_menu_name);
        food_count = findViewById(R.id.single_menu_count);
        MaterialButton btn_add = findViewById(R.id.single_menu_plus_btn);
        MaterialButton btn_min = findViewById(R.id.single_menu_min_btn);
        MaterialButton btn_order = findViewById(R.id.single_menu_order_btn);

        String id_food = getIntent().getStringExtra("food_id");
        Log.i(TAG, "ID Food ==> " + id_food);
        reference.document(id_food).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                if (doc != null) {
                    url_food_img = doc.getString("url");
                    Picasso.get().load(url_food_img).into(food_img);
                    txt_name_food = doc.getString("name");
                    food_name.setText(txt_name_food);
                }
            } else {
                Log.e(TAG, "Error ==> " + task.getException());
            }
        });
        /* ORDER COUNTER */
        order_count = 1;
        food_count.setText(String.valueOf(order_count));

        btn_min.setOnClickListener(v -> {
            if (order_count == 1){
                Toast.makeText(this, "You can't order zero :(",
                        Toast.LENGTH_SHORT).show();
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

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            Intent intent = new Intent(SingleMenuActivity.this,
                    HomeActivity.class);
            intent.putExtra("order", new OrderModel(id_food,
                    url_food_img,
                    txt_name_food,
                    String.valueOf(order_count),
                    ts));
            startActivity(intent);
        });
    }
}