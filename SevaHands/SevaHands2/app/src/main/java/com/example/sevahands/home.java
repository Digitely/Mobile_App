package com.example.sevahands;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sevahands.databinding.ActivityHomeBinding;

public class home extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;

                case R.id.navigation_contact:
                    startActivity(new Intent(getApplicationContext(),contact.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;

                case R.id.navigation_donate:
                    startActivity(new Intent(getApplicationContext(), donate.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;



            }
            return false;
        });

    }
}