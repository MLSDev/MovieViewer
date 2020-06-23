package com.shykun.volodymyr.movieviewer.presentation.model

data class SearchItem(
        val id: Int,
        val itemType: ItemType,
        val imageUrl: String?,
        val text: String
)