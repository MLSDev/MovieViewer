package com.shykun.volodymyr.movieviewer.presentation.people.details

import com.shykun.volodymyr.movieviewer.data.entity.Role
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderHorizontalItemRoleBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder

class PersonCastViewHolder(private val binding: ViewHolderHorizontalItemRoleBinding) : BaseViewHolder<Role>(binding) {

    override fun bind(item: Role, position: Int) {
        super.bind(item, position)

        executeBinding(item)
    }

    private fun executeBinding(role: Role) {
        binding.role = role
        binding.executePendingBindings()
    }
}