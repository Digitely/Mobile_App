package com.example.sevahandsversionone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AdminSchool : AppCompatActivity() {
    private lateinit var editTextSchoolName: EditText
    private lateinit var buttonAddSchool: Button
    private lateinit var buttonUploadImage: Button

    private val storageRef = FirebaseStorage.getInstance().reference
    private var selectedImageUri: Uri? = null

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    // User selected an image
                    selectedImageUri = imageUri
                    // Now you can upload selectedImageUri to Firebase Storage when the button is clicked
                    uploadImageToFirebase()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_school)

        editTextSchoolName = findViewById(R.id.editTextSchoolName)
        buttonAddSchool = findViewById(R.id.buttonAddSchool)
        buttonUploadImage = findViewById(R.id.BtnImage)

        buttonUploadImage.setOnClickListener {
            // Prompt the user to select an image from files
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            pickImage.launch(intent)
        }

        buttonAddSchool.setOnClickListener {
            val schoolName = editTextSchoolName.text.toString().trim()

            // Check if school name is not empty and an image is selected
            if (schoolName.isNotEmpty() && selectedImageUri != null) {
                uploadImageToFirebase()
            } else {
                // Handle empty school name input or no image selected
                if (schoolName.isEmpty()) {
                    editTextSchoolName.error = "Please enter a school name"
                } else {
                    // Show a toast message indicating that the user needs to add an image
                    Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadImageToFirebase() {
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        val uploadTask = imageRef.putFile(selectedImageUri!!)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully, now get the download URL
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                // Now you can use imageUrl, for example, save it to the database
            }.addOnFailureListener {
                // Handle failures while getting the download URL
            }
        }.addOnFailureListener { exception ->
            // Handle failures while uploading the image
        }
    }
}
