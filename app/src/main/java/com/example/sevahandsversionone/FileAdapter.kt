package com.example.sevahandsversionone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class FileAdapter(private val files: MutableList<UploadedFile>) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]
        holder.fileNameTextView.text = file.fileName

        Picasso.get().load(file.downloadUrl).into(holder.imageView)

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            // Get a reference to the file in Firebase Storage
            val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(file.downloadUrl)

            // Delete the file from Firebase Storage
            storageRef.delete()
                .addOnSuccessListener {
                    // File deleted successfully, now remove the item from the list
                    files.removeAt(position)
                    // Notify adapter about the item removal
                    notifyItemRemoved(position)
                }
                .addOnFailureListener {
                    // Handle any errors that occurred during the delete operation
                    // You can show a toast or dialog to inform the user about the error
                }
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.fileNameTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete)
        val imageView: ImageView = itemView.findViewById(R.id.fileImageView)

    }
}
