package com.example.sevahandsversionone


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Events : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
     //   bottomNavigationView.selectedItemId = R.id.navigation_events

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                /*
                R.id.navigation_events -> {
                    return@setOnItemSelectedListener true
                }

                 */

                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }



            }
            return@setOnItemSelectedListener false
        }
    }
}
