package com.example.sevahandsversionone

import android.service.quicksettings.Tile
import androidx.recyclerview.widget.RecyclerView

data class ServiceItem(
   // val title: String,
    val subTitle: String,
    val description: String,
    val imageResId: Int // Resource ID for the image
)

val serviceItems = listOf(
    ServiceItem(
        "What we do",
        "Seva Hands uplifts the lives of vulnerable sectors of our community through nutrition based" +
                "programs to improve and increase capacity building in marginalized communities, protecting the" +
                "lives of vulnerable women, the aged and children ,empowering the youth through capacity building" +
                "through knowledge,psycho social engineering through various methods and disaster relief drives",
        R.drawable.service1
    ),
    ServiceItem(
        "How we do it",
        "As women we wear many hats in our lives – wife, mother," +
                "daughter, sister, friend and employee however in all these roles" +
                "we play, Seva Hands will not be who we are without our donors, sponsors" +
                "and our army of volunteers. We are indebted to our support team it is only" +
                "through their kindness and generosity we are able to accomplish the things we do.",
        R.drawable.service2
    ),
            ServiceItem(
            "Lives benefited",
    "Seva Hands flagship project, the Weekly Sandwich Drive, serves over 500 lunches. " +
            "We also distribute hampers in our annual drive and on an adhoc basis. We are in " +
            "touch with Senior Citizens homes and Homes of Safety, assisting where we can. " +
            "During the school holidays we distribute the bread, we are fortunate enough to " +
            "receive, to the various Homes and communities around us.",

    R.drawable.service3
)
)
