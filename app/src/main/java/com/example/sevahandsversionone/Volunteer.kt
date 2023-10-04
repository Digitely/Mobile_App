package com.example.sevahandsversionone

data class Volunteer(
    val ID :String,
    val company: String,
    val email: String,
    val item: String,
    val message: String,
    val name: String,
    val number: String,
    val quantity: String,
    val type: String
) {
    // Empty constructor required for Firebase
    constructor() : this("", "", "", "", "", "", "", "", "")
}
