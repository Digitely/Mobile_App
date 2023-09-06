package com.example.sevahandsversionone

data class ServiceItem(
    val title: String,
    val description: String,
    val imageResId: Int // Resource ID for the image
)

val serviceItems = listOf(
    ServiceItem(
        "Service 1",
        "Description for Service 1",
        R.drawable.homep// Replace with your actual image resource
    ),
    ServiceItem(
        "Service 2",
        "Description for Service 2",
        R.drawable.homep // Replace with your actual image resource
    )
)
