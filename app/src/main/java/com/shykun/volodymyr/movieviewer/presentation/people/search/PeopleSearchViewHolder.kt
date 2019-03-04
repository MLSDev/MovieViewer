package com.shykun.volodymyr.movieviewer.presentation.people.search

import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchPeopleBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class PeopleSearchViewHolder(
        private val binding: ItemSearchPeopleBinding,
        private val clickSubject: PublishSubject<Int>) : BaseViewHolder<Person>(binding) {

    override fun bind(item: Person?) {
        super.bind(item)

        itemView.setOnClickListener {
            if (item != null) {
                clickSubject.onNext(item.id)
            }
        }
        executeBinding()
    }

    private fun executeBinding() {
        binding.person = item
        binding.executePendingBindings()
    }
}