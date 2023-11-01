package com.example.sevahandsversionone

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
  private lateinit var userEmail: EditText
  private lateinit var Password: EditText
  private lateinit var btnlogin: Button
  private lateinit var btnGoogle: Button

  private lateinit var mAuth: FirebaseAuth
  private lateinit var mGoogleApiClient: GoogleApiClient

  @SuppressLint("CutPasteId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    // Initialize views and Firebase Authentication
    userEmail = findViewById(R.id.userEmail)
    Password = findViewById(R.id.password)
    btnlogin = findViewById(R.id.btnLogin)
    mAuth = FirebaseAuth.getInstance()
    btnGoogle = findViewById(R.id.GoogleLogin)
    // Set click listener for login button
    btnlogin.setOnClickListener {
      loginUser()
    }
    btnGoogle.setOnClickListener {
      // Initiate Google Sign-In
      val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
      startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    val forgotPass: TextView = findViewById(R.id.forgotPass)

    // Set an OnClickListener on the EditText
    forgotPass.setOnClickListener {
      // Create a dialog to prompt the user for their email
      val dialogBuilder = AlertDialog.Builder(this)
      val editText = EditText(this)
      dialogBuilder.setTitle("Enter your email")
        .setView(editText)
        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
          // Handle the user input (email) here
          val email = editText.text.toString()
          // Now you can use 'email' to perform the necessary actions
          mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
              if (task.isSuccessful) {
                // Password reset email sent successfully
                // Handle success, show a toast message, etc.
              } else {
                // Password reset email failed
                // Handle failure, show a toast message, etc.
              }
            }
        })
        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
          dialog.cancel()
        })
        .show()
    }

    // Set click listener for "Create Account" text
    val textViewClickable = findViewById<TextView>(R.id.reglink)
    textViewClickable.setOnClickListener {
      // Navigate to registration activity
      val intent = Intent(this@Login, Regindiv::class.java)
      startActivity(intent)
    }

    // Configure Google Sign-In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(getString(R.string.default_web_client_id))
      .requestEmail()
      .build()

    mGoogleApiClient = GoogleApiClient.Builder(this)
      .enableAutoManage(this) { /* Handle error */ }
      .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
      .build()
  }

  // Function to handle login process
  private fun loginUser() {
    // Your existing login code

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

  // Handle the result of Google Sign-In
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == RC_SIGN_IN) {
      val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
      if (result != null) {
        if (result.isSuccess) {
          val account = result.signInAccount
          if (account != null) {
            firebaseAuthWithGoogle(account)
          }
        } else {
          // Google Sign-In failed, handle this case
          Toast.makeText(this, "Google Sign-In failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
    val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
    mAuth.signInWithCredential(credential)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          // Sign in success, update UI
          val intent = Intent(this@Login, Home::class.java)
          startActivity(intent)
          finish()
        } else {
          // If sign in fails, display a message to the user.
          Toast.makeText(this, "Google Sign-In failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
      }
  }


  companion object {
    private const val RC_SIGN_IN = 9001
  }
}
