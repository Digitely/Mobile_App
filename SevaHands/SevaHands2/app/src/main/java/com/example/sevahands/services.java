package com.example.sevahands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class services extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.setSelectedItemId(R.id.navigation_Explore);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
               /* case R.id.navigation_Explore:
                    return true;
                    */


                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;

               /* case R.id.navigation_contact:
                    startActivity(new Intent(getApplicationContext(), contact.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;

                */

                case R.id.navigation_donate:
                    startActivity(new Intent(getApplicationContext(),donate.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;





            }
            return false;
        });
    }
}

