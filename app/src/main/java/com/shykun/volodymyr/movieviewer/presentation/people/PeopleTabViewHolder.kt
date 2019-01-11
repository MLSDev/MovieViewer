package com.shykun.volodymyr.movieviewer.presentation.people

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import com.shykun.volodymyr.movieviewer.presentation.glide.GlideApp
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_person.view.*

open class BasePeopleViewHolder(itemView: View)
    : BaseViewHolder<Person>(itemView)

class PeopleTabLoadingViewHolder(itemView: View)
    : BasePeopleViewHolder(itemView)

class PeopleTabViewHolder(itemView: View)
    : BasePeopleViewHolder(itemView) {

    private val name: TextView = itemView.personName
    private val photo: ImageView = itemView.personPhoto

    override fun bind(item: Person, position: Int) {
        super.bind(item, position)

        name.text = item.name
        GlideApp.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${item.profilePath}")
                .error(R.drawable.ic_error)
                .into(photo)
    }
}