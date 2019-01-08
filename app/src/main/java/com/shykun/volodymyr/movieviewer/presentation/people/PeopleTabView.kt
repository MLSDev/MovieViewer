package com.shykun.volodymyr.movieviewer.presentation.people

import com.arellomobile.mvp.MvpView
import com.shykun.volodymyr.movieviewer.data.entity.Person

interface PeopleTabView : MvpView {
    fun showPeople(people: ArrayList<Person>)
    fun showError()
}