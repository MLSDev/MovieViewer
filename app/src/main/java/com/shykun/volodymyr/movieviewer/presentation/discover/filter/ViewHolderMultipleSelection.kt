package com.shykun.volodymyr.movieviewer.presentation.discover.filter

import com.shykun.volodymyr.movieviewer.databinding.ItemMultipleSelectionBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder

class ViewHolderMultipleSelection(
        private val binding: ItemMultipleSelectionBinding,
        private val clickListener: OnItemClickListener) : BaseViewHolder<String>(binding) {


    fun bind(item: String, isChecked: Boolean) {
        super.bind(item)

        binding.root.setOnClickListener {
            clickListener.onItemClicked(adapterPosition)
        }

        executeBinding(isChecked)
    }

    private fun executeBinding(isChecked: Boolean) {
        binding.filterName = item
        binding.isChecked = isChecked
        binding.executePendingBindings()
    }
}