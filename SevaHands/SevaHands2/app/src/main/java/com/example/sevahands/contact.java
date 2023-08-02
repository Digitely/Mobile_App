package com.example.sevahands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        //bottomNavigationView.setSelectedItemId(R.id.navigation_contact);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
              /* case R.id.navigation_contact:
                    return true; */

                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;

                case R.id.navigation_donate:
                    startActivity(new Intent(getApplicationContext(), donate.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;

              /*  case R.id.navigation_Explore:
                    startActivity(new Intent(getApplicationContext(),services.class));
                    overridePendingTransition(R.transition.right, R.transition.left);
                    finish();
                    return true;
*/




            }
            return false;
        });
    }
}
