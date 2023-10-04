package com.example.sevahandsversionone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize


class admin_volunteers : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var volunteerAdapter: VolunteerAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_volunteers)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        database = FirebaseDatabase.getInstance().reference.child("volunteers")

        // Initialize an empty list to hold the volunteer data
        val volunteersData: MutableList<Volunteer> = mutableListOf()

        // Create a ValueEventListener to fetch data from Firebase
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the previous data
                volunteersData.clear()

                // Iterate through the snapshot and convert data to Volunteer objects
                for (data in snapshot.children) {
                    val volunteer = data.getValue(Volunteer::class.java)
                    volunteer?.let {
                        volunteersData.add(it)
                    }
                }
                Log.d("VolunteersData", volunteersData.toString())
                // Update the RecyclerView adapter with the new data
                val onDeleteClickListener: (Volunteer) -> Unit = { volunteer ->
                    // Handle delete operation here, for example:
                    deleteVolunteer(volunteer)
                }

                volunteerAdapter = VolunteerAdapter(volunteersData, onDeleteClickListener)
                recyclerView.adapter = volunteerAdapter


            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        }

        // Add the ValueEventListener to the database reference
        database.addValueEventListener(valueEventListener)
    }
    private fun decodeEmail(encodedEmail: String): String {
        return String(Base64.decode(encodedEmail, Base64.NO_WRAP))
    }
    private fun encodeEmail(email: String): String {
        return Base64.encodeToString(email.toByteArray(), Base64.NO_WRAP)
    }

    private fun deleteVolunteer(volunteer: Volunteer) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirm Deletion")
        alertDialogBuilder.setMessage("Are you sure you want to delete this volunteer?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->

            val encodedEmail = encodeEmail(volunteer.email)
            val volunteerRef = database.child(encodedEmail)
            Log.d("FirebasePath", volunteerRef.toString())

            val childUpdates: MutableMap<String, Any?> = mutableMapOf()
            childUpdates[volunteerRef.key.toString()] = null


            // Update the child node with a null value
            database.child("volunteers").updateChildren(childUpdates)
                .addOnSuccessListener {
                    // Handle successful deletion
                    showToast("Volunteer deleted successfully.")
                }
                .addOnFailureListener { e ->
                    // Handle deletion failure
                    Log.e("FirebaseDeletion", "Deletion failed. Error: ${e.message}")
                    showErrorDialog("Deletion failed. Please try again.")
                }
                .addOnCompleteListener {
                    // Refresh the RecyclerView after deletion
                    refreshRecyclerView()
                }
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun refreshRecyclerView() {
        val volunteersData: MutableList<Volunteer> = mutableListOf()

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                volunteersData.clear()

                for (data in snapshot.children) {
                    val volunteer = data.getValue(Volunteer::class.java)
                    volunteer?.let {
                        volunteersData.add(it)
                    }
                }

                // Update the RecyclerView adapter with the new data
                volunteerAdapter.updateData(volunteersData)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        }

        // Add the ValueEventListener to the database reference
        database.addValueEventListener(valueEventListener)
    }





    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorDialog(errorMessage: String) {
        val errorDialogBuilder = AlertDialog.Builder(this)
        errorDialogBuilder.setTitle("Error")
        errorDialogBuilder.setMessage(errorMessage)
        errorDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val errorDialog = errorDialogBuilder.create()
        errorDialog.show()
    }



}