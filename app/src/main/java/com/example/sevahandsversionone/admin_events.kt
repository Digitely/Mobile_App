package com.example.sevahandsversionone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class admin_events : AppCompatActivity() {

    private val eventList = ArrayList<Event>() // Initialize an empty list of events
    private val db = FirebaseFirestore.getInstance() // Initialize Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_events)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        val buttonAddEventPage = findViewById<Button>(R.id.buttonAddEventPage)
        buttonAddEventPage.setOnClickListener {
            // Navigate to the "Add Event" page (you should define this activity)
            navigateToAddEventPage()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = EventAdapter(
            eventList,
            onEditClickListener = { event ->
                // Handle edit click if needed
                Log.d("testing","button has been pushed")
                val editIntent = Intent(this, EditEventActivity::class.java)
                editIntent.putExtra("event", event) // event is the selected Event object
                startActivity(editIntent)
            },
            onDeleteClickListener = { event ->
                // Delete the record from Firestore based on event name
                showDeleteConfirmationDialog(event )
            }
        )

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
                    val inputFormatter = SimpleDateFormat("MMMM d, yyyy 'at' h:mm:ss a", Locale.getDefault())

                    try {
                        // Parse the date string into a Date object
                        val dateObject: Date = inputFormatter.parse(dateString)

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


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_events -> {
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
                R.id.navigation_school -> {
                    startActivity(Intent(applicationContext, admin_school::class.java))
                    overridePendingTransition(R.transition.right, R.transition.left)
                    finish()
                    return@setOnItemSelectedListener true
                }


            }
            return@setOnItemSelectedListener false
        }



    }


    fun showDeleteConfirmationDialog(event: Event) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Deletion")
        builder.setMessage("Are you sure you want to delete this event?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            // Delete the record from Firestore based on event name
            db.collection("Events")
                .document(event.name) // Assuming event name is the document ID
                .delete()
                .addOnSuccessListener {
                    // Handle successful deletion
                    Toast.makeText(this, "Event deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Handle deletion failure
                    Toast.makeText(this, "Failed to delete event: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }



    private val onEditClickListener: (Event) -> Unit = { event ->
        // Handle edit operation here
        Log.d("testing","button has been pushed")
        val editIntent = Intent(this, EditEventActivity::class.java)
        editIntent.putExtra("event", event) // event is the selected Event object
        startActivity(editIntent)
    }
    fun navigateToAddEventPage() {
        // Navigate to the "Add Event" page (you should define this activity)
        val intent = Intent(this, AddEvents::class.java)
        startActivity(intent)
    }
}
