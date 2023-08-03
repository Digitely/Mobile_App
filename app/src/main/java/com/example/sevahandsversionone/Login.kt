package com.example.sevahandsversionone



import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    // private lateinit var btnRegister: Button
    // private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Casting for variables
        btnLogin = findViewById(R.id.btnLogin)
        // btnRegister = findViewById(R.id.RegisterBtn)
        // btnCancel = findViewById(R.id.CancelBtn)

        btnLogin.setOnClickListener {
            // Method call
            loginUser()
        }


    }



    private fun loginUser() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}
