package com.shykun.volodymyr.movieviewer.presentation.common.adapters

import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalListBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalListItem
import io.reactivex.subjects.PublishSubject

class HorizontalListViewHolder(
        private val binding: ItemHorizontalListBinding,
        private val clickSubject: PublishSubject<HorizontalListItem>) : BaseViewHolder<HorizontalListItem>(binding) {

    override fun bind(item: HorizontalListItem?) {
        super.bind(item)

        if (item != null)
            itemView.setOnClickListener { clickSubject.onNext(item) }

        executeBinding()
    }

    private fun executeBinding() {
        binding.item = item
        binding.executePendingBindings()
    }
}