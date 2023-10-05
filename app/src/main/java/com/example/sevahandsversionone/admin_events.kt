package com.example.sevahandsversionone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val onDeleteClickListener: (Volunteer) -> Unit = { volunteer ->
            // Handle delete operation here, for example:
            deleteVolunteer(volunteer)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventAdapter(eventList, onDeleteClickListener)
        recyclerView.adapter = adapter

        // Retrieve data from Firebase Firestore
        val db = FirebaseFirestore.getInstance()
        val eventsCollection = db.collection("Events")
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
                    val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
                    val timeFormatter = SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH)

                    try {
                        // Parse the date string into a Date object
                        val dateObject: Date = dateFormatter.parse(dateString)

                        // Format the date and time components into separate variables
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



    }

    private fun deleteVolunteer(volunteer: Volunteer) {
        TODO("Not yet implemented")
    }


    fun navigateToAddEventPage() {
        // Navigate to the "Add Event" page (you should define this activity)
        val intent = Intent(this, AddEvents::class.java)
        startActivity(intent)
    }
}
