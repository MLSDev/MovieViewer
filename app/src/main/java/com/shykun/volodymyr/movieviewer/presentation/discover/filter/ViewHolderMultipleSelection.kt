package com.shykun.volodymyr.movieviewer.presentation.discover.filter

import com.shykun.volodymyr.movieviewer.databinding.ItemMultipleSelectionBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder

class ViewHolderMultipleSelection(
        private val binding: ItemMultipleSelectionBinding,
        private val clickListener: OnItemClickListener) : BaseViewHolder<String>(binding) {


    fun bind(item: String, position: Int, isChecked: Boolean) {
        super.bind(item, position)

        binding.root.setOnClickListener {
            clickListener.onItemClicked(position)
        }

        executeBinding(item, isChecked)
    }

    private fun executeBinding(value: String, isChecked: Boolean) {
        binding.filterName = value
        binding.isChecked = isChecked
        binding.executePendingBindings()
    }
}