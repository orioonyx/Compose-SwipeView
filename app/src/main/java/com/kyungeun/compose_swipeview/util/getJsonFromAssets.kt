package com.kyungeun.compose_swipeview.util

import android.content.Context
import java.io.IOException
import java.io.InputStream

private fun getJSONFromAssets(context: Context): String {
    val myUsersJsonFile = context.assets.open("productTestData.json")
    return myUsersJsonFile.toString()
}