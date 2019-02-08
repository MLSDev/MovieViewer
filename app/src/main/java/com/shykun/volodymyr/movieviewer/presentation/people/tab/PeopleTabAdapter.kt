package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.ItemLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ItemPersonBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

const val PERSON = 0
const val LOADING = 1


class PeopleTabAdapter(items: ArrayList<Person>)
    : BaseRecyclerViewAdapter<Person, BasePeopleViewHolder>(items) {

    private val clickSubject = PublishSubject.create<Int>()

    val personClickEvent: Observable<Int> = clickSubject
    var lastLoadedPage = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasePeopleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == PERSON) {
            val binding = DataBindingUtil.inflate<ItemPersonBinding>(
                    inflater,
                    R.layout.item_person,
                    parent,
                    false)

            PeopleTabViewHolder(binding, clickSubject)
        } else {
            val binding = DataBindingUtil.inflate<ItemLoadingBinding>(
                    inflater,
                    R.layout.item_loading,
                    parent,
                    false)

            PeopleTabLoadingViewHolder(binding)
        }

    }

    override fun onBindViewHolder(viewHolder: BasePeopleViewHolder, position: Int) {
        if (viewHolder !is PeopleTabLoadingViewHolder)
            super.onBindViewHolder(viewHolder, position)
    }

    fun addPeople(people: List<Person>) {
        items.addAll(people)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size)
            PERSON
        else
            LOADING

    }

    override fun getItemCount() = items.size + 1
}