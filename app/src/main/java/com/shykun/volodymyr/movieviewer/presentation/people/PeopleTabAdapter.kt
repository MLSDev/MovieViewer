package com.shykun.volodymyr.movieviewer.presentation.people

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.presentation.base.BaseRecyclerViewAdapter
import java.util.zip.Inflater

class PeopleTabAdapter(items: ArrayList<Person>)
    : BaseRecyclerViewAdapter<Person, PeopleTabViewHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleTabViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_person, parent, false)
        return PeopleTabViewHolder(view, clickSubject)
    }

    fun addPeople(people: ArrayList<Person>) {
        items.addAll(people)
        notifyDataSetChanged()
    }
}