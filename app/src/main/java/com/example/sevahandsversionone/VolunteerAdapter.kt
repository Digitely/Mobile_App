package com.example.sevahandsversionone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import  com.example.sevahandsversionone.admin_volunteers


class VolunteerAdapter(
    private var volunteers: List<Volunteer>,
    private val onDeleteClickListener: (Volunteer) -> Unit
) : RecyclerView.Adapter<VolunteerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.volunteers_list, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val volunteer = volunteers[position]
        holder.bind(volunteer)

        // Set click listener for the Delete button
        holder.btnDelete.setOnClickListener {
            // Call the lambda function to handle delete button click
            onDeleteClickListener.invoke(volunteer)
        }
    }

    fun updateData(newVolunteers: List<Volunteer>) {
        volunteers = ArrayList(newVolunteers)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return volunteers.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)
        private val numberTextView: TextView = itemView.findViewById(R.id.textViewNumber)
        private val messageTextView: TextView = itemView.findViewById(R.id.textViewMessage)
        private val typeTextView: TextView = itemView.findViewById(R.id.textViewType)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(volunteer: Volunteer) {
            nameTextView.text = "Name: ${volunteer.name}"
            emailTextView.text = "Email: ${volunteer.email}"
            numberTextView.text = "Number: ${volunteer.number}"
            messageTextView.text = "Message: ${volunteer.message}"
            typeTextView.text = "Type: ${volunteer.type}"
        }
    }
}

