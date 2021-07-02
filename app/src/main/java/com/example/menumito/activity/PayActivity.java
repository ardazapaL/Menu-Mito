package com.example.menumito.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.menumito.R;
import com.example.menumito.adapter.CheckoutAdapter;
import com.example.menumito.model.OrderModel;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    private static final String TAG = "PayActivity";
    private ArrayList<OrderModel> checkoutModels;
    private RecyclerView recyclerView;
    private CheckoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

       /* Bundle data = getIntent().getExtras();
        if (data != null){

            checkoutModels = new ArrayList<>();
            OrderModel newCheckoutModel = data.getParcelable("order_submit");
            checkoutModels.add(newCheckoutModel);
            Log.i(TAG, "Order count ==> " + checkoutModels.size());

            for (int i = 0; i < checkoutModels.size(); i++) {
                OrderModel model = checkoutModels.get(i);
                Log.i(TAG, "List Order ==> " + model.getId());
            }
        }*/

        checkoutModels = HomeActivity.getArrayList();

        for (int i = 0; i < checkoutModels.size(); i++) {
            OrderModel model = checkoutModels.get(i);
            Log.i(TAG, "List Order ==> " + model.getId());
        }
    }
}