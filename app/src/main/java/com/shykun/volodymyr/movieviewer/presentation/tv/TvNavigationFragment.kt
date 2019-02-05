package com.shykun.volodymyr.movieviewer.presentation.tv

import android.os.Bundle
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TV_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys

class TvNavigationFragment : TabNavigationFragment() {

    override val navigationKey = NavigationKeys.TV_NAVIGATION_KEY

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) == null)
            cicerone.router.navigateTo(TV_TAB_FRAGMENT_KEY)
    }
}