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
        "Seva Hands flagship project, our Sandwich Drive, serves over 500 lunches a week to learners in the Reservoir Hills\n" +
                "and Clare Estate community. During the dark times of COVID we sustained our efforts and were buoyed by the testimony\n" +
                "of teachers that shared stories of guaranteed attendance on the days we supplied lunches. One sandwich can make a difference\n" +
                "- we have seen this for ourselves. The larger message through this time was the fact that we were not just providing a sandwich, \n" +
                "we were steadfast in our support. Hope, faith and love came in the form of a sandwich.",
        R.drawable.service1
    ),
    ServiceItem(
        "How we do it",
        "   Our annual Diwali Hamper Drive has been a staple of Seva Hands since 2019 and accompanying this annual\n" +
                "   drive is the provision of ad-hoc hampers. As Seva Hands grew organically, our community faced a battery\n" +
                "   of challenges and we geared ourselves to support them. The power of collective efforts saw us supporting \n" +
                "   those in need during COVID, the floods and riots. Being receptive to our community is what drives us as we\n" +
                "   know the value of uplifting those in need when they are feeling the lowest.",
        R.drawable.service2
    ),
    ServiceItem(
        "Lives benefited",
        "  Seva Hands also provides sandwiches to the local Senior Citizens while they are waiting for their pension payouts.\n" +
                "  We maintain contact with the local Senior Citizens homes and Homes of Safety, assisting where we can. During the\n" +
                "  school holidays we distribute the bread, we are fortunate enough to receive, to the various Homes and communities \n" +
                "  around us. We have witnessed the joy on the faces of those receiving the simplest of gifts and that fills our cups!",

        R.drawable.service3
    )
)