package com.shykun.volodymyr.movieviewer.presentation.discover.filter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.ItemMultipleSelectionBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter

class MultipleSelectionAdapter(items: ArrayList<String>)
    : BaseRecyclerViewAdapter<String, ViewHolderMultipleSelection>(items), OnItemClickListener {

    var checkedItems = HashMap<String, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMultipleSelection {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMultipleSelectionBinding>(
                inflater,
                R.layout.item_multiple_selection,
                parent,
                false)


        return ViewHolderMultipleSelection(binding, this)
    }

    override fun onItemClicked(position: Int) {
        val item = items[position]
        val isChecked = checkedItems[item]

        if (isChecked == null)
            checkedItems[item] = true
        else
            checkedItems[item] = !isChecked

        notifyItemChanged(position)
    }

    override fun onBindViewHolder(viewHolder: ViewHolderMultipleSelection, position: Int) {
        val item = items[position]
        val isChecked = checkedItems[item] ?: false
        viewHolder.bind(item, position, isChecked)
    }

    fun getCheckedItems(): ArrayList<String> {
        val res = ArrayList<String>()
        for ((item, isChecked) in checkedItems) {
            if (isChecked)
                res.add(item)
        }
        return res
    }
}