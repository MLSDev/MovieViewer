package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.databinding.ViewDataBinding
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.ItemLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ItemPersonBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject

open class BasePeopleViewHolder(viewDataBinding: ViewDataBinding)
    : BaseViewHolder<Person>(viewDataBinding)

class PeopleTabLoadingViewHolder(binding: ItemLoadingBinding)
    : BasePeopleViewHolder(binding)

class PeopleTabViewHolder(private val binding: ItemPersonBinding, private val personClickSubject: PublishSubject<Int>)
    : BasePeopleViewHolder(binding) {

    override fun bind(item: Person, position: Int) {
        super.bind(item, position)

        itemView.setOnClickListener { personClickSubject.onNext(item.id) }
        executeBinding(item)
    }

    private fun executeBinding(person: Person) {
        binding.person = person
        binding.executePendingBindings()
    }
}