package com.example.sevahandsversionone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sevahandsversionone.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        val tempAdminButton = findViewById<Button>(R.id.TempAdmin)

        val donate = findViewById<Button>(R.id.btnDonate)

        donate.setOnClickListener {
            // Handle the button click here


            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/donate/?hosted_button_id=9A7RM3N4NQV3G"))
            startActivity(browserIntent)
        }

        val textViewLogout = findViewById<TextView>(R.id.textViewLogout);
        textViewLogout.setOnClickListener {
            // Handle the button click here
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, Login::class.java)
            startActivity(intent) // Start the login page
        }


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


    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                // Start AlgorithmActivity
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                // Start CourseActivity
                val intent = Intent(this, DashBoard::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_contact -> {
                // Start ProfileActivity
                val intent = Intent(this, Contact::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }






}
