package com.shykun.volodymyr.movieviewer.presentation.people.details

import com.shykun.volodymyr.movieviewer.data.entity.Role
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalRoleBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class PersonCastViewHolder(
        private val binding: ItemHorizontalRoleBinding,
        private val clickSubject: PublishSubject<Role>) : BaseViewHolder<Role>(binding) {

    override fun bind(item: Role, position: Int) {
        super.bind(item, position)

        itemView.setOnClickListener { clickSubject.onNext(item) }
        executeBinding(item)
    }

    private fun executeBinding(role: Role) {
        binding.role = role
        binding.executePendingBindings()
    }
}