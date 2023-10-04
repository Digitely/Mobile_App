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
        private val companyTextView: TextView = itemView.findViewById(R.id.companyTextView)
        private val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
        private val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)
        private val numberTextView: TextView = itemView.findViewById(R.id.textViewNumber)
        private val messageTextView: TextView = itemView.findViewById(R.id.textViewMessage)
        private val typeTextView: TextView = itemView.findViewById(R.id.textViewType)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(volunteer: Volunteer) {
            // Set name TextView
            if (volunteer.name.isNotEmpty()) {
                nameTextView.text = "Name: ${volunteer.name}"
                nameTextView.visibility = View.VISIBLE
            } else {
                nameTextView.visibility = View.GONE
            }

            // Set company TextView
            if (volunteer.company.isNotEmpty()) {
                companyTextView.text = "Company: ${volunteer.company}"
                companyTextView.visibility = View.VISIBLE
            } else {
                companyTextView.visibility = View.GONE
            }

            // Set item TextView
            if (volunteer.item.isNotEmpty()) {
                itemTextView.text = "Item: ${volunteer.item}"
                itemTextView.visibility = View.VISIBLE
            } else {
                itemTextView.visibility = View.GONE
            }

            // Set quantity TextView
            if (volunteer.quantity.isNotEmpty()) {
                quantityTextView.text = "Quantity: ${volunteer.quantity}"
                quantityTextView.visibility = View.VISIBLE
            } else {
                quantityTextView.visibility = View.GONE
            }

            // Set email TextView
            if (volunteer.email.isNotEmpty()) {
                emailTextView.text = "Email: ${volunteer.email}"
                emailTextView.visibility = View.VISIBLE
            } else {
                emailTextView.visibility = View.GONE
            }

            // Set number TextView
            if (volunteer.number.isNotEmpty()) {
                numberTextView.text = "Number: ${volunteer.number}"
                numberTextView.visibility = View.VISIBLE
            } else {
                numberTextView.visibility = View.GONE
            }

            // Set message TextView
            if (volunteer.message.isNotEmpty()) {
                messageTextView.text = "Message: ${volunteer.message}"
                messageTextView.visibility = View.VISIBLE
            } else {
                messageTextView.visibility = View.GONE
            }

            // Set type TextView
            if (volunteer.type.isNotEmpty()) {
                typeTextView.text = "Type: ${volunteer.type}"
                typeTextView.visibility = View.VISIBLE
            } else {
                typeTextView.visibility = View.GONE
            }
        }
    }


}

