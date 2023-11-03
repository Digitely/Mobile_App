package com.example.sevahandsversionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label

class AdminHome : AppCompatActivity() {
    private lateinit var viewEventButton: Button // Declare the button variable
    private lateinit var viewVolunteersButton: Button
    private lateinit var AddGallart: Button
    private lateinit var counter: Button
    private lateinit var School: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        // Initialize the button using findViewById
      //  viewEventButton = findViewById(R.id.viewEvent)

        // Set an OnClickListener for the button
       /* viewEventButton.setOnClickListener {
            // Start the AdminEventsActivity when the button is clicked
            val intent = Intent(this, admin_events::class.java)
            startActivity(intent)
                         }
                         */


            val textViewLogout = findViewById<TextView>(R.id.textViewLogout)

        textViewLogout.setOnClickListener {
                // Handle the button click here
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Login::class.java)
                startActivity(intent) // Start the login page
            }

           // viewVolunteersButton = findViewById(R.id.viewVolunteers)

            // Set OnClickListener for the viewVolunteersButton
          /*  viewVolunteersButton.setOnClickListener {
                // Handle button click here
                val intent = Intent(this, admin_volunteers::class.java)
                startActivity(intent)
            }
            */
        School = findViewById(R.id.BtnSchool)

        // Set OnClickListener for the viewVolunteersButton
        School.setOnClickListener {
            // Handle button click here
            val intent = Intent(this, AdminSchool::class.java)
            startActivity(intent)
        }
        counter = findViewById(R.id.Btncounter)

        // Set OnClickListener for the viewVolunteersButton
        counter.setOnClickListener {
            // Handle button click here
            val intent = Intent(this, Counter::class.java)
            startActivity(intent)
        }

        AddGallart = findViewById(R.id.ADDGalary)

        // Set OnClickListener for the viewVolunteersButton
        AddGallart.setOnClickListener {
            // Handle button click here
            val intent = Intent(this, GalleryAdd::class.java)
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
            R.id.navigation_school -> {
                startActivity(Intent(applicationContext, admin_school::class.java))
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