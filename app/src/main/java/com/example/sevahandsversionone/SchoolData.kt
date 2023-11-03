package com.example.sevahandsversionone

data class SchoolData(
    var schoolId: String = "",
    var schoolName: String = "",
    var imageUrl: String = ""
) {
    // No-argument constructor required by Firebase
    constructor() : this("", "", "")
}
