package com.shykun.volodymyr.movieviewer.presentation.profile

import android.os.Bundle
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys

class ProfileNavigationFragment : TabNavigationFragment() {

    override val navigationKey = NavigationKeys.PROFILE_NAVIGATION_KEY

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) == null)
            cicerone.router.navigateTo(PROFILE_FRAGMENT_KEY)
    }

    override fun backToRoot() {
        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) != null)
            cicerone.router.backTo(PROFILE_FRAGMENT_KEY)
    }
}