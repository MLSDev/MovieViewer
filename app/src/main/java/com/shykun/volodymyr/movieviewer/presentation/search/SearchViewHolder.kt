package com.shykun.volodymyr.movieviewer.presentation.search

import com.shykun.volodymyr.movieviewer.databinding.ItemSearchBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.model.SearchItem
import io.reactivex.subjects.PublishSubject

class SearchViewHolder(
        private val binding: ItemSearchBinding,
        private val clickSubject: PublishSubject<Int>) : BaseViewHolder<SearchItem>(binding) {

    override fun bind(item: SearchItem?) {
        super.bind(item)

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item.id)
            }
        }
        executeBinding()
    }

    private fun executeBinding() {
        binding.item = item
        binding.executePendingBindings()
    }
}