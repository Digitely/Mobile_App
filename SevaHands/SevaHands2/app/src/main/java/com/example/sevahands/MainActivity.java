package com.example.sevahands;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    // Variables needed for splash screen

    ImageView imgLogo;
    long delay = 5000; // holds the amount of time for the page to be changed

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLogo = findViewById(R.id.splashImg);

        // timer settings
        Timer RunSplash = new Timer();
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {

                finish();
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);

            }
        };

        RunSplash.schedule(ShowSplash, delay);

    }
}