package com.example.sevahandsversionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminHome : AppCompatActivity() {
    private lateinit var viewEventButton: Button // Declare the button variable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        // Initialize the button using findViewById
        viewEventButton = findViewById(R.id.viewEvent)

        // Set an OnClickListener for the button
        viewEventButton.setOnClickListener {
            // Start the AdminEventsActivity when the button is clicked
            val intent = Intent(this, admin_events::class.java)
            startActivity(intent)
        }
    }
}