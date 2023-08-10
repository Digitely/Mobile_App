package com.example.sevahandsversionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
  private lateinit var regbtn: EditText
  private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regorg)
      busName=findViewById(R.id.orgName)
      conName=findViewById(R.id.orgOwner)
      conNumber=findViewById(R.id.orgCell)
      email= findViewById(R.id.orgEmail)
      Password = findViewById(R.id.orgPassword)
      conPassword = findViewById(R.id.orgConPassword)
      regbtn = findViewById(R.id.btnRegOrg)
        mAuth=FirebaseAuth.getInstance()
      regbtn.setOnClickListener{
        registerUser()
        }
    }
  private fun registerUser() {
    try {
      val email = email.text.toString().trim()
      val passowrd = Password.text.toString().trim()
      val conPassword = conPassword.text.toString().trim()


      if(TextUtils.isEmpty(email)){
        Toast.makeText(this,"Please enter an email address! ",Toast.LENGTH_SHORT).show()
        return
      }
    if (TextUtils.isEmpty(passowrd)){
      Toast.makeText(this, "Please confirm password",Toast.LENGTH_SHORT).show()
      return
    }
      if (TextUtils.isEmpty(conPassword)){
        Toast.makeText(this, "Please confirm password!",Toast.LENGTH_SHORT).show()
      }
      mAuth.createUserWithEmailAndPassword(email,passowrd).addOnCompleteListener {task ->
        if (task.isSuccessful){
          Toast.makeText(this,"Registration Successful!",Toast.LENGTH_SHORT).show()
          val intent = Intent(this@Regorg,Login::class.java)
          startActivity(intent)
        }else{
          Toast.makeText(this, "Registration Unsuccesful! Please try again",Toast.LENGTH_SHORT).show()
        }
      }
    } catch (eer:Exception){
      Toast.makeText(this, "Error Ocurred:" + eer.message, Toast.LENGTH_SHORT ).show()
    }
  }

}
