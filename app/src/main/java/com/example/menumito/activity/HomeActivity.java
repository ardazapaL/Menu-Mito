package com.example.menumito.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.menumito.R;
import com.google.android.material.tabs.TabLayout;

import com.example.menumito.adapter.TabHomeAdapter;
import com.example.menumito.fragment.GalleryMenuFragment;
import com.example.menumito.fragment.MainMenuFragment;

public class HomeActivity extends AppCompatActivity {

    private ImageButton btn_logout;
    private TabHomeAdapter tabHomeAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.view_pager_home);
        tabLayout = findViewById(R.id.tab_layout_home);
        btn_logout = findViewById(R.id.btn_logout);

        /* tab icons */
        int[] tabIcons = {
                R.drawable.ic_baseline_view_list_24, //icon tab for main menu
                R.drawable.ic_baseline_fastfood_24}; // icon tab for image mode

        /* add com.example.menumito.fragment to tab com.example.menumito.adapter */
        tabHomeAdapter = new TabHomeAdapter(getSupportFragmentManager());
        tabHomeAdapter.addFragment(new MainMenuFragment(), null);
        tabHomeAdapter.addFragment(new GalleryMenuFragment(), null);

        viewPager.setAdapter(tabHomeAdapter);
        tabLayout.setupWithViewPager(viewPager);

        /* set icon tab layout */
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
}