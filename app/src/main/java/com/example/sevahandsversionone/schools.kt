package com.example.sevahandsversionone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class schools : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var schoolAdapter: SchoolAdapter
    private val schoolList: MutableList<SchoolData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schools)

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.SchoolUser)
        schoolAdapter = SchoolAdapter(schoolList, isAdmin = false) // isAdmin is false for regular users
        recyclerView.adapter = schoolAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch schools data from Firebase Realtime Database
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("schools")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                schoolList.clear() // Clear the existing list before adding new data

                for (schoolSnapshot in snapshot.children) {
                    val school = schoolSnapshot.getValue(SchoolData::class.java)
                    if (school != null) {
                        schoolList.add(school) // Add each school to the list
                    }
                }

                schoolAdapter.notifyDataSetChanged() // Notify the adapter that the data has changed
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors, if any
            }
        })
    }
}
