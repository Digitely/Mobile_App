package com.example.sevahandsversionone
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
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

    override  fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)


      userEmail = findViewById(R.id.userEmail)
      Password = findViewById(R.id.password)
      btnlogin = findViewById(R.id.btnLogin)
      mAuth = FirebaseAuth.getInstance()
      btnlogin.setOnClickListener{
        loginUser()
      }

      //when the user clicks on the create account text - they will be routed to the regindiv page
      val textViewClickable = findViewById<TextView>(R.id.reglink)
      textViewClickable.setOnClickListener {
        // Create an Intent to navigate to the SecondActivity
        val intent = Intent(this@Login, Regindiv::class.java)
        startActivity(intent)
      }
    }
  private fun loginUser() {
    {
      val intent = Intent(this, Home::class.java)
      startActivity(intent)
      finish()
    }

    try {
      val email = userEmail.text.toString().trim()
      val password = Password.text.toString().trim()

      if (TextUtils.isEmpty(email)){
        Toast.makeText(this, "Please enter an email address!", Toast.LENGTH_SHORT).show()
        userEmail.requestFocus()
        return
      }
      if (TextUtils.isEmpty(password)) {
        Toast.makeText(this, "Please enter a password!",Toast.LENGTH_SHORT).show()
        Password.requestFocus()
        return
      }
      mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {val intent = Intent (this@Login, Task::class.java)
          startActivity(intent)

          Toast.makeText(this,"Login Successful!", Toast.LENGTH_SHORT).show()
          userEmail.setText("")
          Password.setText("")
          userEmail.requestFocus()
      }else {
        Toast.makeText(this,"Login Unsucessful! Please try again." , Toast.LENGTH_SHORT).show()
          userEmail.setText("")
          Password.setText("")
          userEmail.setText("")
        }
    }
  } catch (eer: Exception) {
  Toast.makeText(this, "Error Occured:" + eer.message, Toast.LENGTH_SHORT).show()
  }
  }
  fun reg(view: View)
  {
    val intent = Intent (this, Regindiv::class.java)
    startActivity(intent)
  }
  fun clearbtn (view: View)
  {
    userEmail.text.clear()
    Password.text.clear()
  }


}
