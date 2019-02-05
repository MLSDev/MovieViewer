package com.shykun.volodymyr.movieviewer.presentation.movies

import android.os.Bundle
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MOVIE_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys

class MoviesNavigationFragment : TabNavigationFragment() {

    override val navigationKey = NavigationKeys.MOVIES_NAVIGATION_KEY

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) == null)
            cicerone.router.navigateTo(MOVIE_TAB_FRAGMENT_KEY)
    }
}