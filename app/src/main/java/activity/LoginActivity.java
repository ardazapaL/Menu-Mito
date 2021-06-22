package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.menumito.R;
import com.google.android.material.tabs.TabLayout;

import adapter.TabLoginAdapter;
import fragment.GuestEntryFragment;
import fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        /* set tab adapter of viewpager */
        TabLoginAdapter tabAdapter = new TabLoginAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new LoginFragment(), "Login");
        tabAdapter.addFragment(new GuestEntryFragment(), "Entry");
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}