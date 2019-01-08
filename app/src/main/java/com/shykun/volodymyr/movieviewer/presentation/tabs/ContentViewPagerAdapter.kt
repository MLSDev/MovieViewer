package com.shykun.volodymyr.movieviewer.presentation.tabs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.shykun.volodymyr.movieviewer.presentation.movies.MovieTabFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.TvTabFragment

class ContentViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> MovieTabFragment()
        1 -> TvTabFragment()
        else -> null
    }

    override fun getPageTitle(position: Int): String? = when (position) {
        0 -> "Movies"
        1 -> "Tv"
        else -> null
    }

    override fun getCount() = 2
}
