package com.example.sevahandsversionone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServiceAdapter(private val serviceItems: List<ServiceItem>) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.services_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val serviceItem = serviceItems[position]
        holder.bind(serviceItem)
    }

    override fun getItemCount(): Int {
        return serviceItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize UI components from recycler_view_item.xml
        private val titleTextView: TextView = itemView.findViewById(R.id.TVHeading)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.TVDescription)
        private val imageView: ImageView = itemView.findViewById(R.id.ServicesImg)

        fun bind(serviceItem: ServiceItem) {
            // Bind data to UI components
            titleTextView.text = serviceItem.title
            descriptionTextView.text = serviceItem.description
            imageView.setImageResource(serviceItem.imageResId)
        }
    }
}
