package com.shykun.volodymyr.movieviewer.presentation.tabs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.shykun.volodymyr.movieviewer.presentation.movies.MoviesFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.TVFragment

class ContentViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> MoviesFragment()
        1 -> TVFragment()
        else -> null
    }

    override fun getPageTitle(position: Int): String? = when (position) {
        0 -> "Movies"
        1 -> "TV"
        else -> null
    }

    override fun getCount() = 2
}
