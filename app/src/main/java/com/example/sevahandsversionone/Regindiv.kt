package com.example.sevahandsversionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Regindiv : AppCompatActivity() {
  //private lateinit var tvhead: TextView
  private lateinit var fullName: EditText
  private lateinit var conNumber: EditText
  private lateinit var userEmail: EditText
  private lateinit var Password: EditText
  private lateinit var conPassword: EditText
  private lateinit var regbtn: Button
  private lateinit var mAuth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_regindiv)

    fullName = findViewById(R.id.editTextTextPassword5)
    conNumber = findViewById(R.id.editTextTextPassword3)
    userEmail = findViewById(R.id.editTextTextPassword2)
    Password = findViewById(R.id.editTextTextPassword)
    conPassword = findViewById(R.id.editTextTextPassword4)
    regbtn = findViewById(R.id.button3)
    mAuth = FirebaseAuth.getInstance()
    regbtn.setOnClickListener{
      registerUser()
    }

  }
  private fun registerUser(){
    try {
      val email = userEmail.text.toString().trim()
      val Password = Password.text.toString().trim()
      val confirmPassword = conPassword.text.toString().trim()

      if (TextUtils.isEmpty(email))
      {
        Toast.makeText(this,"please enter an email address!",Toast.LENGTH_SHORT).show()
        return
      }
      if (TextUtils.isEmpty(Password))
      {
        Toast.makeText(this,"please enter a password!",Toast.LENGTH_SHORT).show()
        return
      }
      if (TextUtils.isEmpty(confirmPassword))
      {
        Toast.makeText(this,"please confirm password!",Toast.LENGTH_SHORT).show()
        return
      }

      mAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener {task ->
        if (task.isSuccessful){
          Toast.makeText(this, "Registration Sucessful!",Toast.LENGTH_SHORT).show()
          val intent = Intent (this@Regindiv,Login::class.java)
          startActivity(intent)
        }else {
          Toast.makeText(this, "Registration Unsucessful! Please try again ",Toast.LENGTH_SHORT).show()
        }
      }
    } catch (eer:Exception){
      Toast.makeText(this, "Error occured " + eer.message, Toast.LENGTH_SHORT).show()
    }
  }
}



