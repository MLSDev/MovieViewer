package com.shykun.volodymyr.movieviewer.presentation.people

import android.view.View
import android.widget.ProgressBar
import com.shykun.volodymyr.movieviewer.data.entity.Person
import com.shykun.volodymyr.movieviewer.presentation.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.view_holder_loading.view.*

class LoadingViewHolder(itemView: View, clickSubject: PublishSubject<Person>)
    : BasePeopleViewHolder(itemView, clickSubject) {

    private val progressBar: ProgressBar = itemView.loadingProgressBar

}