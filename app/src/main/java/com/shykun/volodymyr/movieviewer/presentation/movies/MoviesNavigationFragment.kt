package com.shykun.volodymyr.movieviewer.presentation.movies

import android.os.Bundle
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.base.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MOVIE_TAB_FRAGMENT_KEY

class MoviesNavigationFragment : TabNavigationFragment() {

    override val navigationKey = "MOVIES_NAVIGATION_KEY"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) == null)
            cicerone.router.replaceScreen(MOVIE_TAB_FRAGMENT_KEY)
    }
}