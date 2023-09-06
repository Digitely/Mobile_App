package com.example.sevahandsversionone


import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : AppCompatActivity() {

    // Variables needed for splash screen
    private lateinit var imgLogo: ImageView
    private val delay: Long = 5000 // holds the amount of time for the page to be changed

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgLogo = findViewById(R.id.splashImg)

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // User is already signed in, redirect to the home page
            val intent = Intent(this@MainActivity, Home::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is not signed in, proceed with the timer task
            val showSplash = object : TimerTask() {
                override fun run() {
                    finish()
                    val intent = Intent(this@MainActivity, Login::class.java)
                    startActivity(intent)
                }
            }

            // Schedule the timer task to show the login page
            Timer().schedule(showSplash, delay) // Adjust the delay as needed
        }


    }
    }
