package com.example.sevahandsversionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminHome : AppCompatActivity() {
    private lateinit var viewEventButton: Button // Declare the button variable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        // Initialize the button using findViewById
        viewEventButton = findViewById(R.id.viewEvent)

        // Set an OnClickListener for the button
        viewEventButton.setOnClickListener {
            // Start the AdminEventsActivity when the button is clicked
            val intent = Intent(this, admin_events::class.java)
            startActivity(intent)
        }


    bottomNavigationView.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@setOnItemSelectedListener true
            }
            R.id.navigation_events -> {
                startActivity(Intent(applicationContext, admin_events::class.java))
                overridePendingTransition(R.transition.right, R.transition.left)
                finish()
                return@setOnItemSelectedListener true
            }
            R.id.navigation_volunteers -> {
                startActivity(Intent(applicationContext, admin_volunteers::class.java))
                overridePendingTransition(R.transition.right, R.transition.left)
                finish()
                return@setOnItemSelectedListener true
            }

            R.id.navigation_users -> {
                startActivity(Intent(applicationContext, admin_users::class.java))
                overridePendingTransition(R.transition.right, R.transition.left)
                finish()
                return@setOnItemSelectedListener true
            }
        }
        return@setOnItemSelectedListener false
    }
}

private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Start AlgorithmActivity
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_volunteers -> {
                    // Start CourseActivity
                    val intent = Intent(this, admin_volunteers::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_users -> {
                    // Start ProfileActivity
                    val intent = Intent(this, admin_users::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_events -> {
                    // Start ProfileActivity
                    val intent = Intent(this, admin_events::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                else -> return@OnNavigationItemSelectedListener false
            }
    }
}