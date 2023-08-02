package com.example.sevahands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // casting for variables

        btnLogin = findViewById(R.id.btnLogin);
        //  btnRegister = findViewById(R.id.RegisterBtn);
        //  btnCancel = findViewById(R.id.CancelBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // method call
                loginUser(view);

            }
        });
    }

        /*

       btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // method call
                regUser(view);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and exit the application
            }
        });




    }

    public void regUser (View view)
    {
        Intent intent = new Intent(login.this, regindiv.class);
        startActivity(intent);
        finish();
    }



         */

    public void loginUser (View view)
    {
        Intent intent = new Intent(login.this, home.class);
        startActivity(intent);
        finish();
    }


}