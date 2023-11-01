package com.example.sevahandsversionone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Gallary : AppCompatActivity() {
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageList: MutableList<String> // Assuming a list of image URLs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallary)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Initialize the adapter with the empty image list
        imageList = mutableListOf()
        imageAdapter = ImageAdapter(imageList)
        recyclerView.adapter = imageAdapter

        // Get a reference to your Firebase Storage instance
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference

        // Get a reference to the images folder in Firebase Storage
        val imagesRef: StorageReference = storageRef.child("Gallary")

        // Use Kotlin Coroutines for asynchronous loading
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // List the items in the folder and load images asynchronously
                val listResult = imagesRef.listAll().await()
                for (item in listResult.items) {
                    val uri = item.downloadUrl.await()
                    val imageUrl = uri.toString()

                    // Add the image URL to the list on the main thread
                    launch(Dispatchers.Main) {
                        imageList.add(imageUrl)
                        // Notify the adapter that the data set has changed
                        imageAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                // Handle errors here
                e.printStackTrace()
            }
        }
    }
}
