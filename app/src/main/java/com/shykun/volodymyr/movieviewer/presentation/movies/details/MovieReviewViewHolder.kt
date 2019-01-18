package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.view.View
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import kotlinx.android.synthetic.main.view_holder_review_item.view.*

class MovieReviewViewHolder(itemViw: View) : BaseViewHolder<Review>(itemViw) {

    private val author: TextView = itemViw.reviewAuthor
    private val content: TextView = itemViw.reviewContent
    private var isExpanded = false

    override fun bind(item: Review, position: Int) {
        super.bind(item, position)

        author.text = item.author
        content.text = item.content

        itemView.setOnClickListener {
            when (isExpanded) {
                true -> {
                    content.maxLines = 1
                    isExpanded = false
                }
                false -> {
                    content.maxLines = 100
                    isExpanded = true
                }

            }
        }
    }
}