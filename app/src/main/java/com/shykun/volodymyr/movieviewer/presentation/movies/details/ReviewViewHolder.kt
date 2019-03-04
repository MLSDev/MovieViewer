package com.shykun.volodymyr.movieviewer.presentation.movies.details

import com.shykun.volodymyr.movieviewer.data.entity.Review
import com.shykun.volodymyr.movieviewer.databinding.ItemReviewBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder

class ReviewViewHolder(private val binding: ItemReviewBinding) : BaseViewHolder<Review>(binding) {

    override fun bind(item: Review?) {
        super.bind(item)

        executeBinding()
    }

    private fun executeBinding() {
        binding.review = item
        binding.viewHolder = this
        binding.executePendingBindings()
    }
}