package com.example.sevahandsversionone


import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import java.util.*

class MainActivity : AppCompatActivity() {

    // Variables needed for splash screen
    private lateinit var imgLogo: ImageView
    private val delay: Long = 5000 // holds the amount of time for the page to be changed

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgLogo = findViewById(R.id.splashImg)

        // timer settings
        val runSplash = Timer()
        val showSplash = object : TimerTask() {
            override fun run() {
                finish()
                val intent = Intent(this@MainActivity, Login::class.java)
                startActivity(intent)
            }
        }

        runSplash.schedule(showSplash, delay)
    }
}
