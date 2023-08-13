package com.example.sevahandsversionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
  private lateinit var userEmail: EditText
  private lateinit var Password: EditText
  private lateinit var btnlogin: Button
  private lateinit var mAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    // Initialize views and Firebase Authentication
    userEmail = findViewById(R.id.userEmail)
    Password = findViewById(R.id.password)
    btnlogin = findViewById(R.id.btnLogin)
    mAuth = FirebaseAuth.getInstance()

    // Set click listener for login button
    btnlogin.setOnClickListener {
      loginUser()
    }

    // Set click listener for "Create Account" text
    val textViewClickable = findViewById<TextView>(R.id.reglink)
    textViewClickable.setOnClickListener {
      // Navigate to registration activity
      val intent = Intent(this@Login, Regindiv::class.java)
      startActivity(intent)
    }
  }

  // Function to handle login process
  private fun loginUser() {
    try {
      val email = userEmail.text.toString().trim()
      val password = Password.text.toString().trim()

      // Validate email and password
      if (TextUtils.isEmpty(email)) {
        Toast.makeText(this, "Please enter an email address!", Toast.LENGTH_SHORT).show()
        userEmail.requestFocus()
        return
      }
      if (TextUtils.isEmpty(password)) {
        Toast.makeText(this, "Please enter a password!", Toast.LENGTH_SHORT).show()
        Password.requestFocus()
        return
      }

      // Sign in user with Firebase Authentication
      mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
          // Login successful, navigate to Home activity
          val intent = Intent(this@Login, Home::class.java)
          startActivity(intent)
          finish()
        } else {
          // Login unsuccessful, display error message
          Toast.makeText(this, "Login Unsuccessful! Please try again.", Toast.LENGTH_SHORT).show()
          userEmail.setText("")
          Password.setText("")
          userEmail.requestFocus()
        }
      }
    } catch (eer: Exception) {
      // Handle and display any exceptions that occur during login
      Toast.makeText(this, "Error Occurred: " + eer.message, Toast.LENGTH_SHORT).show()
    }
  }

  // Function to handle "Create Account" button click
  fun reg(view: View) {
    val intent = Intent(this, Regindiv::class.java)
    startActivity(intent)
  }

  // Function to clear input fields
  fun clearbtn(view: View) {
    userEmail.text.clear()
    Password.text.clear()
  }
}
