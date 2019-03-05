package com.shykun.volodymyr.movieviewer.presentation.model

data class SearchListItem(
        val id: Int,
        val itemType: ItemType,
        val imageUrl: String?,
        val text: String
)