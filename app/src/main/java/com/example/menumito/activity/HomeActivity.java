package com.example.menumito.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.menumito.R;
import com.example.menumito.adapter.TabHomeAdapter;
import com.example.menumito.fcm.Data;
import com.example.menumito.fragment.GalleryMenuFragment;
import com.example.menumito.fragment.MainMenuFragment;
import com.example.menumito.model.OrderModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    static ArrayList<OrderModel> orderModels = new ArrayList<>();
    private ArrayList<OrderModel> submit = new ArrayList<>();

    private ImageButton btn_logout, btn_cart;
    private TabHomeAdapter tabHomeAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ExtendedFloatingActionButton fab;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Bundle data = getIntent().getExtras();
        if (data != null) {
            OrderModel newOrderModel = data.getParcelable("order");
            orderModels.add(newOrderModel);
            Log.i(TAG, "Order count ==> " + orderModels.size());

            for (int i = 0; i < orderModels.size(); i++) {
                OrderModel model = orderModels.get(i);
                Log.i(TAG, "List Order ==> " + model.getId());
            }
        }

        viewPager = findViewById(R.id.view_pager_home);
        fab = findViewById(R.id.fab_main_menu);
        tabLayout = findViewById(R.id.tab_layout_home);
        btn_logout = findViewById(R.id.btn_logout);
        btn_cart = findViewById(R.id.btn_cart);

        /* TAB ICONS */
        int[] tabIcons = {
                R.drawable.ic_baseline_view_list_24, // ICON FIRST MENU
                R.drawable.ic_baseline_fastfood_24}; // ICON SECOND MENU

        /* INITIALIZE VIEW PAGER AND TAB */
        tabHomeAdapter = new TabHomeAdapter(getSupportFragmentManager());
        tabHomeAdapter.addFragment(new MainMenuFragment());
        tabHomeAdapter.addFragment(new GalleryMenuFragment());
        viewPager.setAdapter(tabHomeAdapter);
        tabLayout.setupWithViewPager(viewPager);

        /* SET ICON TAB */
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

        /* BUTTON LOGOUT LISTENER */
        btn_logout.setOnClickListener(v -> {

            auth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        });
        /* FAB CLICK LISTENER */
        fab.setOnClickListener(v -> {

            Intent intent = new Intent(HomeActivity.this, CheckoutActivity.class);
            intent.putExtra("order_submit", orderModels);
            startActivity(intent);
        });
    }

    public static ArrayList<OrderModel> getArrayList() { return orderModels; }

    @Override
    protected void onStart() {
        super.onStart();

//        Log.i(TAG, "UID ==> " + user.getUid());
    }
}