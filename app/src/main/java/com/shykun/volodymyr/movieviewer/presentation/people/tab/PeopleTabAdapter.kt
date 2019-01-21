package com.shykun.volodymyr.movieviewer.presentation.people.tab

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderLoadingBinding
import com.shykun.volodymyr.movieviewer.databinding.ViewHolderPersonBinding
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private const val PERSON = 0
private const val LOADING = 1


class PeopleTabAdapter(items: ArrayList<Person>)
    : BaseRecyclerViewAdapter<Person, BasePeopleViewHolder>(items) {

    private val personClickSubject = PublishSubject.create<Int>()

    val personClickEvent: Observable<Int> = personClickSubject
    var lastLoadedPage = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasePeopleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == PERSON) {
            val binding = DataBindingUtil.inflate<ViewHolderPersonBinding>(
                    inflater,
                    R.layout.view_holder_person,
                    parent,
                    false)

            PeopleTabViewHolder(binding, personClickSubject)
        } else {
            val binding = DataBindingUtil.inflate<ViewHolderLoadingBinding>(
                    inflater,
                    R.layout.view_holder_loading,
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