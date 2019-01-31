package com.shykun.volodymyr.movieviewer.presentation.people.search


import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.databinding.ItemSearchPeopleBinding
import com.shykun.volodymyr.movieviewer.presentation.common.BaseRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PeopleSearchAdapter(people: ArrayList<Person>) : BaseRecyclerViewAdapter<Person, PeopleSearchViewHolder>(people) {

    private val clickSubject = PublishSubject.create<Int>()
    val clickObservable: Observable<Int> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSearchPeopleBinding>(
                inflater,
                R.layout.item_search_people,
                parent,
                false)
        return PeopleSearchViewHolder(binding, clickSubject)
    }

    fun setPeople(people: List<Person>) {
        items.apply {
            clear()
            addAll(people)
        }
        notifyDataSetChanged()
    }
}