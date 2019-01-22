package com.shykun.volodymyr.movieviewer.presentation.discover

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderSingleSelectionItemBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class SingleSelectionAdapter(items: ArrayList<String>)
    : BaseRecyclerViewAdapter<String, ViewHolderSingleSelection>(items), OnItemClickListener {

    var lastClickedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSingleSelection {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewHolderSingleSelectionItemBinding>(
                inflater,
                R.layout.view_holder_single_selection_item,
                parent,
                false)

        return ViewHolderSingleSelection(binding, this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolderSingleSelection, position: Int) {
        val item = items[position]
        viewHolder.bind(item, position, position == lastClickedPosition)
    }

    override fun onItemClicked(position: Int) {
        lastClickedPosition = position
    }
}