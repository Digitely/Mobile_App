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

class Regorg : AppCompatActivity() {

  private lateinit var busName: EditText
  private lateinit var conName: EditText
  private lateinit var conNumber: EditText
  private lateinit var email: EditText
  private lateinit var Password: EditText
  private lateinit var conPassword: EditText
  private lateinit var regbtn: Button
  private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regorg)
      busName=findViewById(R.id.editTextTextPassword6)
      conName=findViewById(R.id.editTextTextPassword7)
      conNumber=findViewById(R.id.editTextTextPassword8)
      email= findViewById(R.id.editTextTextPassword10)
      Password = findViewById(R.id.editTextTextPassword9)
      conPassword = findViewById(R.id.editTextTextPassword11)
      regbtn = findViewById(R.id.button)
        mAuth=FirebaseAuth.getInstance()

      regbtn.setOnClickListener{
        registerUser()
        }
    }
  private fun registerUser() {
    try {
      val email = email.text.toString().trim()
      val password = Password.text.toString().trim()
      val confirmPassword = conPassword.text.toString().trim()

      if (TextUtils.isEmpty(email)) {
        Toast.makeText(this, "Please enter an email address!", Toast.LENGTH_SHORT).show()
        return
      }
      if (TextUtils.isEmpty(password)) {
        Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
        return
      }
      if (TextUtils.isEmpty(confirmPassword)) {
        Toast.makeText(this, "Please confirm password!", Toast.LENGTH_SHORT).show()
        return
      }

      mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
          Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
          val intent = Intent(this@Regorg, Login::class.java)
          startActivity(intent)
        } else {
          Toast.makeText(this, "Registration Unsuccessful! Please try again", Toast.LENGTH_SHORT).show()
        }
      }
    } catch (eer: Exception) {
      Toast.makeText(this, "Error Occurred: " + eer.message, Toast.LENGTH_SHORT).show()
    }
  }

}
