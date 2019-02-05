package com.shykun.volodymyr.movieviewer.presentation.discover

import android.os.Bundle
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.DISCOVER_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys

class DiscoverNavigationFragment : TabNavigationFragment() {

    override val navigationKey = NavigationKeys.DISCOVER_NAVIGATION_KEY

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) == null)
            cicerone.router.navigateTo(DISCOVER_FRAGMENT_KEY)
    }
}