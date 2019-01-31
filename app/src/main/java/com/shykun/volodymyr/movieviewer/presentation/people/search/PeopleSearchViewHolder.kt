package com.shykun.volodymyr.movieviewer.presentation.people.search

import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchPeopleBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseViewHolder
import io.reactivex.subjects.PublishSubject

class PeopleSearchViewHolder(
        private val binding: ItemSearchPeopleBinding,
        private val clickSubject: PublishSubject<Int>): BaseViewHolder<Person>(binding) {

    override fun bind(item: Person, position: Int) {
        super.bind(item, position)

        itemView.setOnClickListener { clickSubject.onNext(item.id) }
        executeBinding(item)
    }

    private fun executeBinding(person: Person) {
        binding.person = person
        binding.executePendingBindings()
    }
}