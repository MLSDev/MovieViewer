package com.shykun.volodymyr.movieviewer.presentation.tv.details

import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderRecommendedTvItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

class RecommendedTvViewHolder(private val binding: ViewHolderRecommendedTvItemBinding) : BaseViewHolder<Tv>(binding) {

    override fun bind(item: Tv, position: Int) {
        super.bind(item, position)

        executeBinding(item)
    }

    private fun executeBinding(tv: Tv) {
        binding.tv = tv
        binding.executePendingBindings()
    }
}