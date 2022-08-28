package com.kyungeun.compose_swipeview.data

data class Product(
    val id: String,
    val category: String,
    val tag: String,
    val title: String,
    val subTitle: String,
    val info: String,
    val price: Int,
    val imageList: List<String>
)