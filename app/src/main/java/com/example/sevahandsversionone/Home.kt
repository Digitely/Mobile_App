package com.example.sevahandsversionone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.sevahandsversionone.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        val tempAdminButton = findViewById<Button>(R.id.TempAdmin)

        // Set an OnClickListener for the button
        tempAdminButton.setOnClickListener {
            // Handle the button click here
            val intent = Intent(this, AdminHome::class.java)
            startActivity(intent) // Start the AdminActivity
        }

        val btnExplore = findViewById<Button>(R.id.btnExplore)

        // Set an OnClickListener to navigate to the services page
        btnExplore.setOnClickListener {
            val intent = Intent(this, Services::class.java)
            startActivity(intent) // Start the Services
        }
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_dashboard -> {
                    startActivity(Intent(applicationContext, DashBoard::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_contact -> {
                    startActivity(Intent(applicationContext, Contact::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }


            }
            return@setOnItemSelectedListener false
        }
    }
}
