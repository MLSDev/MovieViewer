package com.shykun.volodymyr.movieviewer.presentation.discover.filter

import com.shykun.volodymyr.movieviewer.databinding.ItemSingleSelectionBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

class ViewHolderSingleSelection(
        private val binding: ItemSingleSelectionBinding,
        private val clickListener: OnItemClickListener) : BaseViewHolder<Int>(binding) {

    fun bind(item: Int, position: Int, isChecked: Boolean) {
        super.bind(item, position)

        binding.root.setOnClickListener { clickListener.onItemClicked(position) }

        executeBinding(item, isChecked)
    }

    private fun executeBinding(filterName: Int, isChecked: Boolean) {
        binding.filterName = filterName
        binding.isChecked = isChecked
        binding.executePendingBindings()
    }
}