package com.example.sevahandsversionone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileAdapter(private val files: MutableList<UploadedFile>) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]
        holder.fileNameTextView.text = file.fileName

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            // Remove the item from the list
            files.removeAt(position)
            // Notify adapter about the item removal
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.fileNameTextView)
        val deleteButton: Button = itemView.findViewById(R.id.delete)
    }
}
