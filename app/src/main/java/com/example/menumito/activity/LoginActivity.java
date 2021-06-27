package com.example.menumito.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.menumito.R;
import com.google.android.material.tabs.TabLayout;

import com.example.menumito.adapter.TabLoginAdapter;
import com.example.menumito.fragment.GuestEntryFragment;
import com.example.menumito.fragment.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private final String LOG = "LoginActivity";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        /* set tab com.example.menumito.adapter of viewpager */
        TabLoginAdapter tabAdapter = new TabLoginAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new LoginFragment(), "Login");
        tabAdapter.addFragment(new GuestEntryFragment(), "Entry");
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        if (user != null){

            /* intent login */
            Intent goLogin = new Intent(this, SendNotif.class);
            goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            Log.d(LOG, user.getUid() +
                    " | " + user.getEmail());
            startActivity(goLogin);
        }
    }
}