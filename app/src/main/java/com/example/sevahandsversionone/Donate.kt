package com.example.sevahandsversionone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Donate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        //bottomNavigationView.selectedItemId = R.id.navigation_donate

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                /*R.id.navigation_donate -> {
                    return@setOnItemSelectedListener true
                }

                 */

                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                /*R.id.navigation_contact -> {
                    startActivity(Intent(applicationContext, ContactActivity::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_Explore -> {
                    startActivity(Intent(applicationContext, ServicesActivity::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }*/

            }
            return@setOnItemSelectedListener false
        }
    }
}
