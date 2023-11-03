package com.example.sevahandsversionone

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class UserEventsAdapter(private var events: List<Event>) : RecyclerView.Adapter<UserEventsAdapter.EventViewHolder>() {

    fun setEventsList(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView = itemView.findViewById(R.id.text_event_name)
        val eventDate: TextView = itemView.findViewById(R.id.text_event_date)
        val eventDescription: TextView = itemView.findViewById(R.id.text_event_description)
        val eventLocation: TextView = itemView.findViewById(R.id.text_event_location)
        val eventTime: TextView = itemView.findViewById(R.id.text_event_time)
        val getDirectionsButton: Button = itemView.findViewById(R.id.btn_get_directions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventName.text = event.name
        holder.eventDate.text = event.date
        holder.eventDescription.text = event.description
        holder.eventLocation.text = event.location
        holder.eventTime.text = event.Time
        // Set click listener for the button if needed
        holder.getDirectionsButton.setOnClickListener {
            val location = event.location // Get the location from your Event object
            Log.d("Location", "Location: $location") // Log the location for debugging purposes

            // Create a Uri object for Google Maps with the location query
            val gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$location")
            Log.d("Location", "Uri: $gmmIntentUri") // Log the Uri for debugging purposes

            val browserIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            holder.itemView.context.startActivity(browserIntent)
        }


    }

    override fun getItemCount(): Int {
        return events.size
    }
}
