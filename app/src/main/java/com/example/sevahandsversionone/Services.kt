package com.example.sevahandsversionone


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Services : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)


        // Control the bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, Home::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    true
                }
                R.id.navigation_contact -> {
                    // Start the Contact activity
                    startActivity(Intent(applicationContext, Contact::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    true
                }
                R.id.navigation_dashboard -> {
                    // Start the Home activity
                    startActivity(Intent(applicationContext, DashBoard::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    true
                }
                else -> false
            }
        }


        // Find the RecyclerView in your layout
        val recyclerView = findViewById<RecyclerView>(R.id.RC_View)

// Create an instance of the ServiceAdapter and pass your serviceItems
        val adapter = ServiceAdapter(serviceItems)

// Set the adapter for the RecyclerView
        recyclerView.adapter = adapter

// Set the layout manager for the RecyclerView (e.g., LinearLayoutManager)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}
