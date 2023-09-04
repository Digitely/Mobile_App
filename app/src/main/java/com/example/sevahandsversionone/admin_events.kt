package com.example.sevahandsversionone

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class admin_events : AppCompatActivity() {


    private val calendar = Calendar.getInstance()
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

        val adapter = EventAdapter(eventList) // Populate eventList with data from Firebase
        recyclerView.adapter = adapter
    }

    fun navigateToAddEventPage() {
        // Navigate to the "Add Event" page (you should define this activity)
        val intent = Intent(this, AddEvents::class.java)
        startActivity(intent)
    }
}







