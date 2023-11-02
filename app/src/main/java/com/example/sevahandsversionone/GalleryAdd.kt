package com.example.sevahandsversionone

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.util.FileUtil
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class GalleryAdd : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var selectFileButton: Button
   // private lateinit var uploadButton: Button
    private lateinit var fileAdapter: FileAdapter
    private val uploadedFiles = mutableListOf<UploadedFile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_add)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
       // recyclerView.layoutManager = LinearLayoutManager(this)
        fileAdapter = FileAdapter(uploadedFiles)
        recyclerView.adapter = fileAdapter

        // Get a reference to your Firebase Storage instance
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("Gallary")
        Log.d("Gallary", "storageRef  "+storageRef.toString())
        // List the items in the folder and load files asynchronously
        storageRef.listAll().addOnSuccessListener { listResult ->
            GlobalScope.launch(Dispatchers.IO) {
                listResult.items.forEach { item ->
                    item.downloadUrl.addOnSuccessListener { uri ->
                        val uploadedFile = UploadedFile(item.name, uri.toString())
                        uploadedFiles.add(uploadedFile)
                        runOnUiThread {
                            fileAdapter.notifyDataSetChanged()
                        }
                        Log.d("Gallary", "File added: ${item.name}, Download URL: $uri")
                    }.addOnFailureListener { exception ->
                        Log.e("Gallary", "Error getting download URL for ${item.name}: ${exception.message}")
                    }
                }
            }
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("Gallary", "Error listing files: ${exception.message}")
            exception.printStackTrace()
        }
        selectFileButton = findViewById(R.id.selectFileButton)
        selectFileButton.setOnClickListener {
            // Call the upload function to start the file upload process
            upload()
        }

    }



    private fun upload() {
        // Use an Intent to pick a file from the device
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // You can set the MIME type to restrict the type of files the user can pick
        startActivityForResult(intent, PICK_FILE_REQUEST)
    }

    // Handle the result of the file picker Intent
    // Handle the result of the file picker Intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            // Get the selected file URI from the Intent
            val fileUri: Uri? = data?.data

            // Proceed with uploading the file to Firebase Storage
            if (fileUri != null) {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference

                // Open an input stream for the content URI using ContentResolver
                val inputStream = contentResolver.openInputStream(fileUri)
                val name = getFileName(fileUri)
                // Create a reference to the "Gallary" folder and the file inside it
                val gallaryRef = storageRef.child("Gallary").child(name)

                // Upload the file using inputStream directly
                val uploadTask = inputStream?.let { gallaryRef.putStream(it) }

                if (uploadTask != null) {
                    uploadTask.addOnSuccessListener {
                        // Handle success
                        // Get the download URL and add the file to the RecyclerView
                        gallaryRef.downloadUrl.addOnSuccessListener { uri ->
                            val uploadedFile = UploadedFile(fileUri.lastPathSegment ?: "Unknown", uri.toString())
                            uploadedFiles.add(uploadedFile)
                            fileAdapter.notifyDataSetChanged()
                        }
                    }.addOnFailureListener {
                        // Handle failure
                        Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun getFileName(uri: Uri): String {
        var fileName: String? = null
        val scheme = uri.scheme
        if (scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    // Get the column index for DISPLAY_NAME
                    val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    // Check if the column exists and is not -1
                    if (displayNameIndex != -1) {
                        // Retrieve the display name
                        fileName = it.getString(displayNameIndex)
                    }
                }
            }
        } else if (scheme == "file") {
            fileName = uri.path
            val cut = fileName?.lastIndexOf('/')
            if (cut != -1) {
                fileName = fileName?.substring(cut!! + 1)
            }
        }
        return fileName ?: "unknown_file"
    }


    companion object {
        const val PICK_FILE_REQUEST = 1 // Request code for the file picker Intent
    }
}

