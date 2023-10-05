package com.example.sevahandsversionone

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

            // Create a Uri object for Google Maps with the location query
            val gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$location")

            // Create an Intent with action VIEW and the Uri
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

            // Set the package to Google Maps (optional, ensures it opens in Google Maps even if other map apps are available)
            mapIntent.setPackage("com.google.android.apps.maps")

            // Check if there is an app to handle this intent before starting the activity
            if (mapIntent.resolveActivity(holder.itemView.context.packageManager) != null) {
                holder.itemView.context.startActivity(mapIntent)
            } else {
                // Handle the case where there is no activity to handle the intent (e.g., show an error message)
            }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }
}
