package com.example.sevahandsversionone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AdminSchool : AppCompatActivity() {
    private lateinit var editTextSchoolName: EditText
    private lateinit var buttonAddSchool: Button
    private lateinit var buttonUploadImage: Button
    private lateinit var schoolAdapter: SchoolAdapter
    private val schoolList: MutableList<SchoolData> = mutableListOf()

    private val storageRef = FirebaseStorage.getInstance().reference
    private var selectedImageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            if (imageUri != null) {
                // User selected an image
                selectedImageUri = imageUri
                // Now you can upload selectedImageUri to Firebase Storage when the button is clicked
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_school)

        // Initialize UI elements
        editTextSchoolName = findViewById(R.id.editTextSchoolName)
        val btnSelectImage = findViewById<Button>(R.id.btnSelectImage)
        val btnCreateSchool = findViewById<Button>(R.id.btnCreateSchool)

        btnSelectImage.setOnClickListener {
            // Prompt the user to select an image from files
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            pickImage.launch(intent)
        }

        btnCreateSchool.setOnClickListener {
            // Create/save the school with the entered text and the selected image
            val schoolName = editTextSchoolName.text.toString().trim()

            if (schoolName.isNotEmpty() && selectedImageUri != null) {
                // Both school name and image are available, proceed to save to database
                uploadImageToFirebase(schoolName, selectedImageUri!!)
            } else {
                // Handle the case when either the school name or the image is missing
                Toast.makeText(this, "Please enter a school name and select an image.", Toast.LENGTH_SHORT).show()
            }
        }

        fetchSchoolDataFromDatabase()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        schoolAdapter = SchoolAdapter(schoolList, true)
        val recyclerView = findViewById<RecyclerView>(R.id.SchoolAdmin)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = schoolAdapter
    }

    private fun fetchSchoolDataFromDatabase() {
        val databaseReference = FirebaseDatabase.getInstance().reference.child("schools")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                schoolList.clear() // Clear the existing list before adding new data

                for (schoolSnapshot in snapshot.children) {
                    val school = schoolSnapshot.getValue(SchoolData::class.java)
                    if (school != null) {
                        schoolList.add(school) // Add each school to the list
                        Log.d("school", school.toString())
                    }
                }

                Log.d("AdminSchool", "Number of schools fetched: ${schoolList.size}")
                schoolAdapter.notifyDataSetChanged() // Notify the adapter that the data has changed
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("AdminSchool", "Database fetch cancelled: ${error.message}")
                // Handle errors, if any
            }
        })
    }

    private fun uploadImageToFirebase(schoolName: String, imageUri: Uri) {
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully, now get the download URL
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                // Now you can use imageUrl, for example, save it to the database
                saveSchoolToDatabase(schoolName, imageUrl)
                Log.d("UploadImage", "Image uploaded successfully. Download URL: $imageUrl")
            }.addOnFailureListener {
                // Handle failures while getting the download URL
                Log.e("UploadImage", "Failed to get download URL: ${it.message}")
            }
        }.addOnFailureListener { exception ->
            // Handle failures while uploading the image
            Log.e("UploadImage", "Image upload failed: ${exception.message}")
        }
    }

    private fun saveSchoolToDatabase(schoolName: String, imageUrl: String) {
        val schoolId = UUID.randomUUID().toString() // Generate a unique ID for the school

        val school = SchoolData(schoolId, schoolName, imageUrl)

        // Save the school object to the "schools" node in the database
        val databaseReference = FirebaseDatabase.getInstance().reference.child("schools")
        databaseReference.child(schoolId).setValue(school)
            .addOnSuccessListener {
                Log.d("SaveToDatabase", "School data saved successfully.")
            }
            .addOnFailureListener { exception ->
                Log.e("SaveToDatabase", "Failed to save school data: ${exception.message}")
            }
    }
}
