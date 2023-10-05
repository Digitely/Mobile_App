package com.example.sevahandsversionone

data class ServiceItem(
    val title: String,
    val description: String,
    val imageResId: Int // Resource ID for the image
)

val serviceItems = listOf(
    ServiceItem(
        "Service 1",
        "Seva Hands uplifts the lives of vulnerable sectors of our community through nutrition based" +
                " programs to improve and increase capacity building in marginalized communities, protecting the " +
                "lives of vulnerable women, the aged and children ,empowering the youth through capacity building " +
                "through knowledge,psycho social sngineering through various methods and disaster relief drives",
        R.drawable.service1// Replace with your actual image resource
    ),
    ServiceItem(
        "Service 2",
        "As women we wear many hats in our lives – wife, mother, " +
                "daughter, sister, friend and employee however in all these roles" +
                " we play, Seva Hands will not be who we are without our donors, sponsors" +
                " and our army of volunteers. We are indebted to our support team it is only " +
                "through their kindness and generosity we are able to accomplish the things we do.",
        R.drawable.service2 // Replace with your actual image resource
    ),
            ServiceItem(
            "Service 3",
    "If we need to put a number in place we have served 41 796 lunches thus far, and we have assembled " +
            "and distributed over 700 Diwali Hampers. Covid Relief Hampers in excess of 350. Sanitary & Care packs" +
            " in excess of 400. Bread Distribution to various Homes of Safety and Old Age homes. You really cannot " +
            "put a figure to the lives you touch(directly or indirectly) when you love what you do and it comes from the heart.",

    R.drawable.service3 // Replace with your actual image resource
)
)
