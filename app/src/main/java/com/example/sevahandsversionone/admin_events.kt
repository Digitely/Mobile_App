package com.example.sevahandsversionone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class admin_events : AppCompatActivity() {

    private val eventList = ArrayList<Event>() // Initialize an empty list of events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_events)

        val buttonAddEventPage = findViewById<Button>(R.id.buttonAddEventPage)
        buttonAddEventPage.setOnClickListener {
            // Navigate to the "Add Event" page (you should define this activity)
            navigateToAddEventPage()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventAdapter(eventList)
        recyclerView.adapter = adapter

        // Retrieve data from Firebase Firestore
        val db = FirebaseFirestore.getInstance()
        val eventsCollection = db.collection("Events")

        eventsCollection.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val eventData = document.data
                    val name = eventData["Name"] as String
                    val description = eventData["Description"] as String
                    val location = eventData["location"] as String
                    val date = eventData["Date"].toString() // Assuming "Date" is a Timestamp

                    // Create an Event object and add it to the eventList
                    val event = Event(name, description, location, date)
                    eventList.add(event)
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failures
                // You can add logging or error handling here
            }
    }

    fun navigateToAddEventPage() {
        // Navigate to the "Add Event" page (you should define this activity)
        val intent = Intent(this, AddEvents::class.java)
        startActivity(intent)
    }
}
