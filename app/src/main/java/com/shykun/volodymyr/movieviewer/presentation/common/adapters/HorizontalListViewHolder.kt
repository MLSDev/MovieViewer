package com.shykun.volodymyr.movieviewer.presentation.common.adapters

import android.view.Gravity
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.model.HorizontalItem
import com.shykun.volodymyr.movieviewer.presentation.model.ItemType
import io.reactivex.subjects.PublishSubject

class HorizontalListViewHolder(
        private val binding: ItemHorizontalBinding,
        private val clickSubject: PublishSubject<HorizontalItem>) : BaseViewHolder<HorizontalItem>(binding) {

    override fun bind(item: HorizontalItem?) {
        super.bind(item)

        if (item != null)
            itemView.setOnClickListener { clickSubject.onNext(item) }

        executeBinding()
    }

    private fun executeBinding() {
        binding.item = item
        if (item?.itemType == ItemType.PERSON) {
            binding.movieFooterText.setLines(2)
            binding.movieFooterText.gravity = Gravity.CENTER
        }
        binding.executePendingBindings()
    }
}