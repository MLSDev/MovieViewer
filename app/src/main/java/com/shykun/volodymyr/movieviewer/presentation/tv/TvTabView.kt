package com.shykun.volodymyr.movieviewer.presentation.tv

import com.arellomobile.mvp.MvpView
import com.shykun.volodymyr.movieviewer.data.entity.Tv

interface TvTabView : MvpView {
    fun showPopularTV(tvList: ArrayList<Tv>)
    fun showTopRatedTV(tvList: ArrayList<Tv>)
    fun showTVOnTheAir(tvList: ArrayList<Tv>)

    fun showError()
}