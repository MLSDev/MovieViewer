package com.shykun.volodymyr.movieviewer.presentation.tv.list

import com.arellomobile.mvp.MvpView
import com.shykun.volodymyr.movieviewer.data.entity.Tv

interface TvListView : MvpView {
    fun showTvList(tvList: ArrayList<Tv>)
    fun showError()
}