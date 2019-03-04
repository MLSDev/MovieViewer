package com.shykun.volodymyr.movieviewer.presentation.discover.filter

import com.shykun.volodymyr.movieviewer.databinding.ItemSingleSelectionBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder

class ViewHolderSingleSelection(
        private val binding: ItemSingleSelectionBinding,
        private val clickListener: OnItemClickListener) : BaseViewHolder<Int>(binding) {

    fun bind(item: Int, isChecked: Boolean) {
        super.bind(item)

        binding.root.setOnClickListener { clickListener.onItemClicked(adapterPosition) }

        executeBinding(isChecked)
    }

    private fun executeBinding(isChecked: Boolean) {
        binding.filterName = item
        binding.isChecked = isChecked
        binding.executePendingBindings()
    }
}