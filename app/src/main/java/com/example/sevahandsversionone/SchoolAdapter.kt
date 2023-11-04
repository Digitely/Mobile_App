package com.example.sevahandsversionone

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class SchoolAdapter(private val schoolItems: List<SchoolData>, private val isAdmin: Boolean) : RecyclerView.Adapter<SchoolAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_school, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schoolItem = schoolItems[position]
        holder.bind(schoolItem)
    }

    override fun getItemCount(): Int {
        return schoolItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val schoolNameTextView: TextView = itemView.findViewById(R.id.TVSchool)
        private val schoolImageView: ImageView = itemView.findViewById(R.id.SChoolImage)


        init {
            if (!isAdmin) {
                itemView.findViewById<Button>(R.id.buttonDeleteEvent).visibility = View.GONE
                // or you can disable the button
                itemView.findViewById<Button>(R.id.buttonDeleteEvent).isEnabled = false
            }
                // Set onClickListener for the delete button
                itemView.findViewById<Button>(R.id.buttonDeleteEvent).setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val school = schoolItems[position]

                        // Delete school from the database and storage
                        deleteSchoolFromDatabaseAndStorage(school)
                    }
                }
            }

            fun bind(schoolItem: SchoolData) {
                schoolNameTextView.text = " ${schoolItem.schoolName}"

                // Load school image using Glide library
                Glide.with(itemView.context)
                    .load(schoolItem.imageUrl)
                    .placeholder(R.drawable.schoolimage) // Placeholder image while loading
                    .into(schoolImageView)
            }
        }

        private fun deleteSchoolFromDatabaseAndStorage(school: SchoolData) {
            // Delete school data from the database
            val imageUrl = school.imageUrl
            if (imageUrl.isNullOrEmpty()) {
                Log.e("SchoolAdapter", "Invalid imageUrl: $imageUrl")
                return
            }
            val databaseReference =
                FirebaseDatabase.getInstance().reference.child("schools").child(school.schoolId)
            databaseReference.removeValue()
                .addOnSuccessListener {
                    Log.d("SchoolAdapter", "School data deleted successfully from database.")
                    // Delete school image from storage
                    val storageRef =
                        FirebaseStorage.getInstance().getReferenceFromUrl(school.imageUrl)
                    storageRef.delete()
                        .addOnSuccessListener {
                            Log.d(
                                "SchoolAdapter",
                                "School image deleted successfully from storage."
                            )
                        }
                        .addOnFailureListener { exception ->
                            Log.e(
                                "SchoolAdapter",
                                "Failed to delete school image from storage: ${exception.message}"
                            )
                        }
                }
                .addOnFailureListener { exception ->
                    Log.e(
                        "SchoolAdapter",
                        "Failed to delete school data from database: ${exception.message}"
                    )
                }
        }
    }

