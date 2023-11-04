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
import com.google.android.material.bottomnavigation.BottomNavigationView
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
                selectedImageUri = imageUri
                uploadImageToFirebase(editTextSchoolName.text.toString().trim(), selectedImageUri!!)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_school)
        initializeUI()
    }

    private fun initializeUI() {
        editTextSchoolName = findViewById(R.id.editTextSchoolName)
        val btnSelectImage = findViewById<Button>(R.id.btnSelectImage)
        val btnCreateSchool = findViewById<Button>(R.id.btnCreateSchool)
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            pickImage.launch(intent)
        }
        btnCreateSchool.setOnClickListener {
            val schoolName = editTextSchoolName.text.toString().trim()
            if (schoolName.isNotEmpty() && selectedImageUri != null) {
                uploadImageToFirebase(schoolName, selectedImageUri!!)
            } else {
                Toast.makeText(this, "Please enter a school name and select an image.", Toast.LENGTH_SHORT).show()
            }
        }
        fetchSchoolDataFromDatabase()
        setupRecyclerView()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_school -> {
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_events -> {
                    startActivity(Intent(applicationContext, admin_events::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_volunteers -> {
                    startActivity(Intent(applicationContext, admin_volunteers::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, AdminHome::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
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
                schoolList.clear()
                for (schoolSnapshot in snapshot.children) {
                    val school = schoolSnapshot.getValue(SchoolData::class.java)
                    if (school != null) {
                        schoolList.add(school)
                        Log.d("school", school.toString())
                    }
                }
                Log.d("AdminSchool", "Number of schools fetched: ${schoolList.size}")
                schoolAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("AdminSchool", "Database fetch cancelled: ${error.message}")
            }
        })
    }

    private fun uploadImageToFirebase(schoolName: String, imageUri: Uri) {
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                saveSchoolToDatabase(schoolName, imageUrl)
                Log.d("UploadImage", "Image uploaded successfully. Download URL: $imageUrl")
            }.addOnFailureListener {
                Log.e("UploadImage", "Failed to get download URL: ${it.message}")
            }
        }.addOnFailureListener { exception ->
            Log.e("UploadImage", "Image upload failed: ${exception.message}")
        }
    }

    private fun saveSchoolToDatabase(schoolName: String, imageUrl: String) {
        val schoolId = UUID.randomUUID().toString()
        val school = SchoolData(schoolId, schoolName, imageUrl)
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
