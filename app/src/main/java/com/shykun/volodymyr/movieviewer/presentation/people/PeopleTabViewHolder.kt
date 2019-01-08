package com.shykun.volodymyr.movieviewer.presentation.people

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_person.view.*

class PeopleTabViewHolder(itemView: View, clickSubject: PublishSubject<Person>)
    : BaseViewHolder<Person>(itemView, clickSubject) {

    private val name: TextView = itemView.personName
    private val photo: ImageView = itemView.personPhoto

    override fun bind(item: Person, position: Int) {
        super.bind(item, position)

        name.text = item.name
        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.profilePath}")
                .into(photo)
    }
}