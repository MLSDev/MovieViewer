package com.shykun.volodymyr.movieviewer.presentation.model

data class HorizontalListItem(
        val id: Int,
        val itemType: ItemType,
        val imageUrl: String?,
        val iconId: Int?,
        val text: String
)