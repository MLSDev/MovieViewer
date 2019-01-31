package com.shykun.volodymyr.movieviewer.presentation.tv.details

import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemRecommendedTvBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class RecommendedTvViewHolder(
        private val binding: ItemRecommendedTvBinding,
        private val clickSubject: PublishSubject<Int>) : BaseViewHolder<Tv>(binding) {

    override fun bind(item: Tv, position: Int) {
        super.bind(item, position)

        itemView.setOnClickListener { clickSubject.onNext(item.id) }
        executeBinding(item)
    }

    private fun executeBinding(tv: Tv) {
        binding.tv = tv
        binding.executePendingBindings()
    }
}