package com.shykun.volodymyr.movieviewer.presentation.people

import android.view.View
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject

open class BasePeopleViewHolder(itemView: View, clickSubject: PublishSubject<Person>)
    : BaseViewHolder<Person>(itemView, clickSubject)