package com.example.sevahandsversionone

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEvents : AppCompatActivity() {
    private val calendar = Calendar.getInstance()
    private lateinit var editTextDescription: EditText
    private lateinit var editTextLocation: EditText
    private lateinit var editTextName: EditText
    private lateinit var buttonAddEvent: Button
    private lateinit var buttonSelectDate: Button
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_events)
        // Initialize the variables for the UI elements
        buttonSelectDate = findViewById(R.id.buttonSelectDate)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextLocation = findViewById(R.id.editTextLocation)
        editTextName = findViewById(R.id.editTextName)
        buttonAddEvent = findViewById(R.id.buttonAddEvent)

        buttonSelectDate.setOnClickListener {
            showDateTimePicker()
        }
        buttonAddEvent.setOnClickListener {
            val date = buttonSelectDate.text.toString()
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
            Log.d("firebase",name)
            firestore.collection("Events")
                .document(name)
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
        private fun showDateTimePicker() {
            // Create a Calendar instance to store the selected date and time
            val calendar = Calendar.getInstance()

            // Create a DatePickerDialog to select the date
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    // Update the calendar with the selected date
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    // Create a TimePickerDialog to select the time
                    val timePicker = TimePickerDialog(
                        this,
                        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                            // Update the calendar with the selected time
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            calendar.set(Calendar.MINUTE, minute)

                            // Format the selected date and time
                            val sdf = SimpleDateFormat("MMMM d, yyyy 'at' h:mm:ss a", Locale.getDefault())
                            val formattedDateTime = sdf.format(calendar.time)

                            // Set the formatted date and time to your UI element (e.g., a TextView or Button)
                            buttonSelectDate.text = formattedDateTime
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false // 24-hour format
                    )

                    // Show the time picker dialog
                    timePicker.show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // Show the date picker dialog
            datePicker.show()
        }
}