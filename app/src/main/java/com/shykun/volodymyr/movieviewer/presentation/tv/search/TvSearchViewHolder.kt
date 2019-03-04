package com.shykun.volodymyr.movieviewer.presentation.tv.search

import com.shykun.volodymyr.movieviewer.data.entity.Tv
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchTvBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class TvSearchViewHolder(
        private val binding: ItemSearchTvBinding,
        private val clickSubject: PublishSubject<Int>) : BaseViewHolder<Tv>(binding) {

    override fun bind(item: Tv?) {
        super.bind(item)

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item.id)
            }
        }
        executeBinding()
    }

    private fun executeBinding() {
        binding.tv = item
        binding.executePendingBindings()
    }
}