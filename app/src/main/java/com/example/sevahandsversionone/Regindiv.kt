package com.example.sevahandsversionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Regindiv : AppCompatActivity() {

  private lateinit var fullName: EditText
  private lateinit var conNumber: EditText
  private lateinit var userEmail: EditText
  private lateinit var password: EditText
  private lateinit var conPassword: EditText
  private lateinit var regbtn: Button
  private lateinit var mAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_regindiv)

    fullName = findViewById(R.id.fullNameEditText)
    conNumber = findViewById(R.id.contactNumberEditText)
    userEmail = findViewById(R.id.emailEditText)
    password = findViewById(R.id.passwordEditText)
    conPassword = findViewById(R.id.confirmPasswordEditText)
    regbtn = findViewById(R.id.registerButton)
    mAuth = FirebaseAuth.getInstance()

    regbtn.setOnClickListener {
      registerUser()
    }

    val companyTextView = findViewById<TextView>(R.id.companyTextView)

    companyTextView.setOnClickListener {
      val intent = Intent(this, Regorg::class.java)
      startActivity(intent)
    }

  }



  private fun registerUser() {
    val email = userEmail.text.toString().trim()
    val userPassword = password.text.toString().trim()
    val confirmPassword = conPassword.text.toString().trim()

    if (TextUtils.isEmpty(email)) {
      Toast.makeText(this, "Please enter an email address!", Toast.LENGTH_SHORT).show()
      return
    }

    if (TextUtils.isEmpty(userPassword)) {
      Toast.makeText(this, "Please enter a password!", Toast.LENGTH_SHORT).show()
      return
    }

    if (TextUtils.isEmpty(confirmPassword)) {
      Toast.makeText(this, "Please confirm the password!", Toast.LENGTH_SHORT).show()
      return
    }

    if (userPassword != confirmPassword) {
      Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
      return
    }

    mAuth.createUserWithEmailAndPassword(email, userPassword)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
          val intent = Intent(this@Regindiv, Login::class.java)
          startActivity(intent)
        } else {
          Toast.makeText(
            this,
            "Registration Unsuccessful! Please try again.",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
  }
}