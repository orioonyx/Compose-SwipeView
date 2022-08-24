package com.kyungeun.compose_swipeview.data

import androidx.annotation.StringRes
import com.ericktijerou.jetpuppy.ui.entity.HomeSectionType

data class Product(
    val id: String,
    val category: String,
    val tag: String,
    val title: String,
    val sub_title: String,
    val imageUrl: String,
    val info: String,
    val price: Int
)