package com.example.sevahandsversionone

import android.app.DatePickerDialog
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class admin_events : AppCompatActivity() {
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var editTextDate: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextLocation: EditText
    private lateinit var editTextName: EditText
    private lateinit var buttonAddEvent: Button
    private lateinit var buttonSelectDate: Button

    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_events)

        // Initialize the variables for the UI elements
        buttonSelectDate = findViewById(R.id.buttonSelectDate)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextLocation = findViewById(R.id.editTextLocation)
        editTextName = findViewById(R.id.editTextName)
        buttonAddEvent = findViewById(R.id.buttonAddEvent)

        buttonSelectDate.setOnClickListener {
            showDatePicker()
        }
        buttonAddEvent.setOnClickListener {
            val date = editTextDate.text.toString()
            val description = editTextDescription.text.toString()
            val location = editTextLocation.text.toString()
            val name = editTextName.text.toString()

            // Create a data map
            val eventData = hashMapOf(
                "Date" to date,
                "Description" to description,
                "Location" to location,
                "Name" to name
            )

            // Add data to Firestore
            firestore.collection("Events")
                .document()
                .set(eventData)
                .addOnSuccessListener {
                    // Data added successfully
                    // You can add any additional logic here
                    finish() // Close this activity
                }
                .addOnFailureListener {
                    // Handle the error
                }
    }
}
    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the calendar with the selected date
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Format the selected date and display it in the EditText
                val sdf = SimpleDateFormat("MMMM d, yyyy 'at' h:mm:ss a z", Locale.getDefault())
                val formattedDate = sdf.format(calendar.time)
                editTextDate.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the date picker dialog
        datePicker.show()
    }

  
}







