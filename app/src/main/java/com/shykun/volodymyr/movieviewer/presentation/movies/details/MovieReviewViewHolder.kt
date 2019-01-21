package com.shykun.volodymyr.movieviewer.presentation.movies.details

import android.databinding.ObservableField
import android.view.View
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderReviewItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import kotlinx.android.synthetic.main.view_holder_review_item.view.*

class MovieReviewViewHolder(private val binding: ViewHolderReviewItemBinding) : BaseViewHolder<Review>(binding) {

    private var isExpanded = false
    val maxLines = ObservableField(5)

    override fun bind(item: Review, position: Int) {
        super.bind(item, position)

        itemView.setOnClickListener {
            isExpanded = when (isExpanded) {
                true -> {
                    maxLines.set(5)
                    false
                }
                false -> {
                    maxLines.set(100)
                    true
                }
            }
        }

        executeBinding(item)
    }

    private fun executeBinding(review: Review) {
        binding.review = review
        binding.viewHolder = this
        binding.executePendingBindings()
    }
}