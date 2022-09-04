package com.kyungeun.compose_swipeview

import com.kyungeun.compose_swipeview.data.Product

object ProductDataManager {

    fun getProduct(): Product {
        return Product(
            id = "1",
            category = "activity",
            tag = "summer leisure",
            title = "Surfing",
            subTitle = "1 Day Surf Lessons",
            info = "This adventure experience will change your life!" +
                    "\n\n" +
                    "Surf lessons in Portugal it’s an experience you’re going to remember. This one day thrilling experience will be the highlight of your holidays!" +
                    "\n\n" +
                    "With qualified lifeguards and surfing instructors, at stunning West of Portugal." +
                    "\n\n" +
                    "At Atlantic Coast Surf School we treat surfers of all levels individually, to give them the best support and help to advance fast." +
                    "\n\n" +
                    "Small group sizes with excellent student to instructor ratio." +
                    "\n\n" +
                    "Our Surf lessons in Portugal, can be taught in English, French, Portuguese and Spanish and German at Atlantic Coast Surf School.",
            price = 150000,
            imageList = imageList

        )
    }

    private val imageList = listOf(
        "https://images.unsplash.com/photo-1551524358-f34c0214781d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2564&q=80",
        "https://images.unsplash.com/photo-1523606772308-64a28db0ef2c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=764&q=80",
        "https://images.unsplash.com/photo-1621951289270-b942dc5f4320?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=627&q=80"
        )
}
