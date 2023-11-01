package com.example.sevahandsversionone

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var imgLogo: ImageView
    private val delay: Long = 5000 // 5 seconds delay for splash screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgLogo = findViewById(R.id.splashImg)

        // Firebase authentication instance
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Check if the user is authenticated
        if (currentUser != null) {
            // User is authenticated

            // Get user's email address
            val userEmailAddress = currentUser.email

            if (userEmailAddress == "admin@sevahands.com") {
                // If the user's email is admin@sevahands.com, redirect to the admin page
                val adminIntent = Intent(this@MainActivity, AdminHome::class.java)
                startActivity(adminIntent)
            } else {
                // If the user's email is not admin@sevahands.com, redirect to the home page
                val homeIntent = Intent(this@MainActivity, Home::class.java)
                startActivity(homeIntent)
            }

            // Finish the current activity to prevent the user from navigating back to the login screen
            finish()
        } else {
            // User is not signed in, proceed with the splash screen delay and redirect to login screen

            // Handler to delay the redirection to the login screen
            Handler().postDelayed({
                val intent = Intent(this@MainActivity, Login::class.java)
                startActivity(intent)
                finish() // Finish the current activity after redirecting
            }, delay)
        }
    }
}
