package com.shykun.volodymyr.movieviewer.presentation.tv

import com.arellomobile.mvp.MvpView
import com.shykun.volodymyr.movieviewer.data.entity.TV

interface TVView : MvpView {
    fun showPopularTV(tvList: ArrayList<TV>)
    fun showTopRatedTV(tvList: ArrayList<TV>)
    fun showTVOnTheAir(tvList: ArrayList<TV>)

    fun showError()
}