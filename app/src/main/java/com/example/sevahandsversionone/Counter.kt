package com.example.sevahandsversionone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*

class Counter : AppCompatActivity() {
    private var counterValue = 0
    private lateinit var databaseReference: DatabaseReference
    private lateinit var counterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        counterTextView = findViewById(R.id.counterTextView)
        val incrementButton = findViewById<Button>(R.id.incrementButton)
        val decrementButton = findViewById<Button>(R.id.decrementButton)

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("counter")

        // Read initial value from Firebase Database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val value = snapshot.value
                    if (value is Long) {
                        counterValue = value.toInt()
                    } else if (value is Double) {
                        counterValue = value.toInt()
                    } else if (value != null) {
                        try {
                            counterValue = (value as String).toInt()
                        } catch (e: NumberFormatException) {
                            // Handle the case where the value is not a valid integer
                            // For example, show an error message or set counterValue to a default value
                        }
                    }

                    counterTextView.text = counterValue.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        incrementButton.setOnClickListener {
            counterValue++
            counterTextView.text = counterValue.toString()
            // Update value in Firebase Database
            databaseReference.setValue(counterValue)
        }

        decrementButton.setOnClickListener {
            counterValue--
            counterTextView.text = counterValue.toString()
            // Update value in Firebase Database
            databaseReference.setValue(counterValue)
        }
    }
}

