import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sevahandsversionone.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.google.type.Date

data class ActivityItem(
    val eventName: String,
    val eventDescription: String,
    val eventLocation: GeoPoint,
    val eventDate: java.util.Date
)

class ActivitiesAdapter(private var activityItems: List<ActivityItem>) :
    RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = activityItems[position]
        holder.textEventName.text = item.eventName
        holder.textEventDescription.text = item.eventDescription
        holder.textEventLocation.text = item.eventLocation.toString()
        // Set other views as needed
    }

    override fun getItemCount(): Int {
        return activityItems.size
    }

    fun setActivityItems(activityItems: List<ActivityItem>) {
        this.activityItems = activityItems
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textEventName: TextView = itemView.findViewById(R.id.text_event_name)
        val textEventDescription: TextView = itemView.findViewById(R.id.text_event_description)
        val textEventLocation: TextView = itemView.findViewById(R.id.text_event_location)
        // Initialize other views
    }
}

class ActivitiesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivitiesAdapter
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        recyclerView = findViewById(R.id.recycler_view)

        // Create and set the adapter
        adapter = ActivitiesAdapter(emptyList())
        recyclerView.adapter = adapter

        // Set the layout manager (e.g., LinearLayoutManager)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve activity items from Firestore
        val collectionRef = firestore.collection("activityItems")
        collectionRef.get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                val activityItems = mutableListOf<ActivityItem>()
                for (document in querySnapshot) {
                    val eventName = document.getString("name")
                    val eventDescription = document.getString("description")
                    val eventLocation = document.getGeoPoint("location")
                    val eventDate = document.getDate("date")

                    eventName?.let { name ->
                        eventDescription?.let { description ->
                            eventLocation?.let { location ->
                                eventDate?.let { date ->
                                    val activityItem = ActivityItem(name, description, location, date)
                                    activityItems.add(activityItem)
                                }
                            }
                        }
                    }
                }
                adapter.setActivityItems(activityItems)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error retrieving documents: $exception")
            }
    }
}