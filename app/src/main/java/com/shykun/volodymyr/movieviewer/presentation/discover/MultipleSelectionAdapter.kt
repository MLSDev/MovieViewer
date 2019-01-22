package com.shykun.volodymyr.movieviewer.presentation.discover

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderMultipleSelectionItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class MultipleSelectionAdapter(items: ArrayList<String>)
    : BaseRecyclerViewAdapter<String, ViewHolderMultipleSelection>(items), OnItemClickListener {

    var checkedItems = HashSet<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMultipleSelection {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderMultipleSelectionItemBinding>(
                inflater,
                R.layout.view_holder_multiple_selection_item,
                parent,
                false)


        return ViewHolderMultipleSelection(binding, this)
    }

    override fun onItemClicked(position: Int) {
        val item = items[position]

        if (checkedItems.contains(item))
            checkedItems.remove(item)
        else
            checkedItems.add(item)
    }

    override fun onBindViewHolder(viewHolder: ViewHolderMultipleSelection, position: Int) {
        val item = items[position]
        val isChecked = checkedItems.contains(item)
        viewHolder.bind(item, position, isChecked)
    }
}