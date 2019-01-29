package com.shykun.volodymyr.movieviewer.presentation.tv.details

import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemRecommendedTvBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder

class RecommendedTvViewHolder(private val binding: ItemRecommendedTvBinding) : BaseViewHolder<Tv>(binding) {

    override fun bind(item: Tv, position: Int) {
        super.bind(item, position)

        executeBinding(item)
    }

    private fun executeBinding(tv: Tv) {
        binding.tv = tv
        binding.executePendingBindings()
    }
}