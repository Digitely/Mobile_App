package com.example.sevahandsversionone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import  com.example.sevahandsversionone.admin_volunteers



class EventAdapter(private val events: List<Event> , private val onDeleteClickListener: (Volunteer) -> Unit) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // ViewHolder class and methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)

        holder.deleteButton.setOnClickListener {
            // Call the lambda function to handle delete button click
            onDeleteClickListener.invoke(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventNameTextView: TextView = itemView.findViewById(R.id.textViewEventName)
        private val eventDescriptionTextView: TextView = itemView.findViewById(R.id.textViewEventDescription)
        private val eventLocationTextView: TextView = itemView.findViewById(R.id.textViewEventLocation)
        private val eventDateTextView: TextView = itemView.findViewById(R.id.textViewEventDate)
        private val eventTimeTextView: TextView = itemView.findViewById(R.id.textViewEventTime)
         val editButton: Button = itemView.findViewById(R.id.buttonEditEvent)
         val deleteButton: Button = itemView.findViewById(R.id.buttonDeleteEvent)


        fun bind(event: Event) {
            eventNameTextView.text = "Event Name: ${event.name}"
            eventDescriptionTextView.text = "Event Description: ${event.description}"
            eventLocationTextView.text = "Event Location: ${event.location}"
            eventDateTextView.text = "Event Date: ${event.date}"
            eventTimeTextView.text = "Event Time: ${event.Time}"
            // Set click listeners for edit and delete buttons
        }
    }
}
