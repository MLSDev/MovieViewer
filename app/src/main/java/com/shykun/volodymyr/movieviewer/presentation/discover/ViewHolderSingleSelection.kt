package com.shykun.volodymyr.movieviewer.presentation.discover

import com.shykun.volodymyr.movieviewer.databinding.ViewHolderSingleSelectionItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

class ViewHolderSingleSelection(
        private val binding: ViewHolderSingleSelectionItemBinding,
        private val clickListener: OnItemClickListener) : BaseViewHolder<String>(binding) {

    fun bind(item: String, position: Int, isChecked: Boolean) {
        super.bind(item, position)

        binding.root.setOnClickListener { clickListener.onItemClicked(position) }

        executeBinding(item, isChecked)
    }

    private fun executeBinding(filterName: String, isChecked: Boolean) {
        binding.filterName = filterName
        binding.isChecked = isChecked
        binding.executePendingBindings()
    }
}