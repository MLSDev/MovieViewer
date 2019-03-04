package com.shykun.volodymyr.movieviewer.presentation.people.details

import com.shykun.volodymyr.movieviewer.data.entity.Role
import com.shykun.volodymyr.movieviewer.databinding.ItemHorizontalRoleBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class PersonCastViewHolder(
        private val binding: ItemHorizontalRoleBinding,
        private val clickSubject: PublishSubject<Role>) : BaseViewHolder<Role>(binding) {

    override fun bind(item: Role?) {
        super.bind(item)

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item)
            }
        }
        executeBinding()
    }

    private fun executeBinding() {
        binding.role = item
        binding.executePendingBindings()
    }
}