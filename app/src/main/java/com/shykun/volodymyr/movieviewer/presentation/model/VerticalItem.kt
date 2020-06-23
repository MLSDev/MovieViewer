package com.shykun.volodymyr.movieviewer.presentation.model

data class VerticalItem(
        val itemType: ItemType,
        val id: Int,
        val title: String,
        val imageUrl: String?,
        val rating: Double,
        val genreIds: List<Int>,
        val releaseDate: String
)

data class VerticalItemList(
        val items: List<VerticalItem>,
        val totalItemsCount: Int
)

