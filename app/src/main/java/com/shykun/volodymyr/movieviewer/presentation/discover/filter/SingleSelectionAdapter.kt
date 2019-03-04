package com.shykun.volodymyr.movieviewer.presentation.discover.filter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.ItemSingleSelectionBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter

class SingleSelectionAdapter
    : BaseRecyclerViewAdapter<Int, ViewHolderSingleSelection>(), OnItemClickListener {

    var lastClickedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSingleSelection {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSingleSelectionBinding>(
                inflater,
                R.layout.item_single_selection,
                parent,
                false)

        return ViewHolderSingleSelection(binding, this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolderSingleSelection, position: Int) {
        val item = items[position]
        viewHolder.bind(item,position == lastClickedPosition)
    }

    override fun onItemClicked(position: Int) {
        notifyItemChanged(lastClickedPosition)
        lastClickedPosition = position
        notifyItemChanged(lastClickedPosition)
    }

    fun getChosenItem() =
            if (lastClickedPosition >= 0)
                items[lastClickedPosition]
            else lastClickedPosition

}