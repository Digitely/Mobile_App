package com.example.sevahandsversionone

import ActivitiesAdapter
import ActivityItem
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.GeoPoint
import com.google.type.Date
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.protobuf.Timestamp
import java.util.*

class Events : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserEventsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)




        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recycler_view)
        adapter = UserEventsAdapter(emptyList()) // Initialize with an empty list
        recyclerView.adapter = adapter

        // Set the layout manager (LinearLayoutManager in this case)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve activity items and set them on the adapter
        val activityItems = retrieveActivityItems() // Replace with your data retrieval logic
        adapter.setEventsList(activityItems)
    }

    private fun retrieveActivityItems(): List<Event> {
        val db = FirebaseFirestore.getInstance()
        val eventsCollection = db.collection("Events")
        val eventList = mutableListOf<Event>()

//this is a test
        eventsCollection.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val eventData = document.data
                    val name = eventData["Name"] as String
                    val description = eventData["Description"] as String
                    val location = eventData["Location"] as String

                    val dateString = eventData["Date"] as String

// Create SimpleDateFormat to format date and time
                    val inputFormatter = SimpleDateFormat("MMMM d, yyyy 'at' h:mm:ss a", Locale.getDefault())

                    try {
                        // Parse the date string into a Date object
                        val dateObject: java.util.Date = inputFormatter.parse(dateString)

                        // Format the date and time components into separate variables
                        val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
                        val timeFormatter = SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH)

                        val dateFormatted = dateFormatter.format(dateObject)
                        val timeFormatted = timeFormatter.format(dateObject)

                        // Create an Event object with formatted date and time
                        val event = Event(name, description, location, dateFormatted, timeFormatted)
                        eventList.add(event)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failures
                // You can add logging or error handling here
            }

        return eventList
    }

}