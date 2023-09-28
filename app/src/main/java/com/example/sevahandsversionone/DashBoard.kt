package com.example.sevahandsversionone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

class DashBoard : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        val galbtnImageView = findViewById<ImageView>(R.id.galbtnImageView)
        val eventsbtnImageView = findViewById<ImageView>(R.id.eventsbtnImageView)
        val DonateimageView = findViewById<ImageView>(R.id.eventsbtnImageView)
        val LocationbtnimageView = findViewById<ImageView>(R.id.LocationbtnimageView)

        galbtnImageView.setOnClickListener {
            // Navigate to the GalleryActivity when the galbtnImageView is clicked
            val intent = Intent(this@DashBoard, Gallery::class.java)
            startActivity(intent)
        }

        eventsbtnImageView.setOnClickListener {
            // Navigate to the EventsActivity when the eventsbtnImageView is clicked
            val intent = Intent(this@DashBoard, Events::class.java)
            startActivity(intent)
        }

        DonateimageView.setOnClickListener {
            // Navigate to the EventsActivity when the eventsbtnImageView is clicked
            val intent = Intent(this@DashBoard, Events::class.java)
            startActivity(intent)
        }

        LocationbtnimageView.setOnClickListener {
            // Navigate to the EventsActivity when the eventsbtnImageView is clicked
            val intent = Intent(this@DashBoard, Events::class.java)
            startActivity(intent)
        }

        // Control the bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    // Handle dashboard selection
                    true
                }
                R.id.navigation_contact -> {
                    // Start the Contact activity
                    startActivity(Intent(applicationContext, Contact::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    true
                }
                R.id.navigation_home -> {
                    // Start the Home activity
                    startActivity(Intent(applicationContext, Home::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
