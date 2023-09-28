package com.example.sevahandsversionone

import ActivitiesAdapter
import ActivityItem
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.GeoPoint
import com.google.type.Date

class Events : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivitiesAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        // Control the bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    startActivity(Intent(applicationContext, DashBoard::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
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


        recyclerView = findViewById(R.id.recycler_view)

        // Create and set the adapter
        adapter = ActivitiesAdapter(emptyList())
        recyclerView.adapter = adapter

        // Set the layout manager (e.g., LinearLayoutManager)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve activity items and set them on the adapter
        val activityItems = retrieveActivityItems() // Replace with your data retrieval logic
        adapter.setActivityItems(activityItems)
    }

    private fun retrieveActivityItems(): List<ActivityItem> {
        // Replace this with your data retrieval logic.
        // Fetch the activity items from a database, API, or any other data source.
        // Create a list of ActivityItem objects based on the retrieved data.
        // Return the list of activity items.

        // For demonstration purposes, manually creating dummy data here:
        val activityItems = listOf(
            ActivityItem("Event 1", "Description 1", GeoPoint(0.0, 0.0), java.util.Date()),
            ActivityItem("Event 2", "Description 2", GeoPoint(0.0, 0.0), java.util.Date()),
            ActivityItem("Event 3", "Description 3", GeoPoint(0.0, 0.0), java.util.Date())
        )

        return activityItems
    }
}