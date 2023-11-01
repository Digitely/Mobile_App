package com.example.sevahandsversionone

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditEventActivity : AppCompatActivity() {
    private lateinit var editTextDescription: EditText
    private lateinit var editTextLocation: EditText
    private lateinit var editTextName: EditText
    private lateinit var buttonAddEvent: Button
    private lateinit var buttonSelectDate: Button
    lateinit  var oldName :String
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        // Receive event data from the intent
        val editedEvent = intent.getParcelableExtra<Event>("event")

        // Find your EditText fields in the XML layout
        buttonSelectDate = findViewById(R.id.buttonSelectDate)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextLocation = findViewById(R.id.editTextLocation)
        editTextName = findViewById(R.id.editTextName)
        buttonAddEvent = findViewById(R.id.buttonAddEvent)

        buttonSelectDate.setOnClickListener {
            // Show date and time picker dialog
            showDateTimePicker()
        }
        // Populate EditText fields with event data if available
        editedEvent?.let {
            editTextName.setText(it.name)
            editTextDescription.setText(it.description)
            editTextLocation.setText(it.location)
            buttonSelectDate.text = it.date+" at "+it.Time
            oldName = it.name
        }

        // Handle the date selection button if needed
        val selectDateButton = findViewById<Button>(R.id.buttonSelectDate)
        selectDateButton.setOnClickListener {
            // Handle date selection logic here
        }

        // Handle the save button click
        val saveButton = findViewById<Button>(R.id.buttonAddEvent)
        saveButton.setOnClickListener {

            db.collection("Events")
                .document(oldName) // Assuming event name is the document ID
                .delete()
                .addOnSuccessListener {
                    // Handle successful deletion

                }
                .addOnFailureListener { e ->
                    // Handle deletion failure

                }

            // Get the edited data from the EditText fields
            val updatedName = editTextName.text.toString()
            val updatedDescription = editTextDescription.text.toString()
            val updatedLocation = editTextLocation.text.toString()
            val updatedDate = buttonSelectDate.text.toString()

            val eventData: MutableMap<String, Any> = HashMap()
            eventData["Date"] = updatedDate
            eventData["Description"] = updatedDescription
            eventData["Location"] = updatedLocation
            eventData["Name"] = updatedName

            // Update specific fields in the Firestore document
            db.collection("Events")
                .document(updatedName) // Assuming updatedName is the document ID
                .set(eventData )
                .addOnSuccessListener {
                    finish()
                }
                .addOnFailureListener { e ->
                    // Handle update failure if needed
                }


        }


        }


    private fun showDateTimePicker() {
        // Create a Calendar instance to store the selected date and time
        val calendar = Calendar.getInstance()

        // Create a DatePickerDialog to select the date
        val datePicker = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
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



