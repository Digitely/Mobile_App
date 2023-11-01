package com.example.sevahandsversionone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.github.kittinunf.fuel.httpPost
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class Contact : AppCompatActivity() {
    private lateinit var sendBtn: ImageView
    private lateinit var messageBox: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        messageBox = findViewById(R.id.messageBox)
        sendBtn =findViewById(R.id.sendBtn)
        sendBtn.setOnClickListener {
            // Call the upload function to start the file upload process
            sendEmail(messageBox.text.toString())
        }


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_contact -> {
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    startActivity(Intent(applicationContext, DashBoard::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, Home::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }

        // Find the ImageButtons
        val instagramButton = findViewById<ImageButton>(R.id.InstagramBtn)
        val facebookButton = findViewById<ImageButton>(R.id.FaceBookBtn)
        val TickTokBtn = findViewById<ImageButton>(R.id.TickTokBtn)
        val linkedin = findViewById<ImageButton>(R.id.linkedin)


        // Set click listeners for Instagram and Facebook buttons
        instagramButton.setOnClickListener {
            openLink("https://www.instagram.com/sevahands/")
        }

        facebookButton.setOnClickListener {
            openLink("https://www.facebook.com/sevahands108/")
        }

        TickTokBtn.setOnClickListener {
            openLink("hhttps://www.tiktok.com/@sevahands108")
        }

        linkedin.setOnClickListener {
            openLink("https://za.linkedin.com/company/seva-hands-enterprise")
        }
    }
    fun sendEmail( message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:sevahands@gmail.com") // Replace with your recipient email address
            putExtra(Intent.EXTRA_SUBJECT, "contact us")
            putExtra(Intent.EXTRA_TEXT, message)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            // Handle case where no email app is available
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
