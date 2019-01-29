package com.shykun.volodymyr.movieviewer.presentation.tv

import android.os.Bundle
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.base.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TV_TAB_FRAGMENT_KEY

class TvNavigationFragment : TabNavigationFragment() {

    override val navigationKey = "TV_NAVIGATION_KEY"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) == null)
            cicerone.router.replaceScreen(TV_TAB_FRAGMENT_KEY)
    }
}