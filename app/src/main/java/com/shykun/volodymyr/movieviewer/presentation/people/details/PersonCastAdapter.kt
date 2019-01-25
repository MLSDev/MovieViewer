package com.shykun.volodymyr.movieviewer.presentation.people.details

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Role
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalRoleBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter

class PersonCastAdapter(private val roles: ArrayList<Role>) : BaseRecyclerViewAdapter<Role, PersonCastViewHolder>(roles) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonCastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemHorizontalRoleBinding>(
                inflater,
                R.layout.item_horizontal_role,
                parent,
                false)

        return PersonCastViewHolder(binding)
    }

    fun addRoles(roles: List<Role>) {
        this.roles.addAll(roles)
        notifyDataSetChanged()
    }
}