package com.shykun.volodymyr.movieviewer.presentation.people

import android.os.Bundle
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.base.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PEOPLE_TAB_FRAGMENT_KEY

class PeopleNavigationFragment : TabNavigationFragment() {

    override val navigationKey = "PEOPLE_NAVIGATION_KEY"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.navigationFragmentContainer) == null)
            cicerone.router.replaceScreen(PEOPLE_TAB_FRAGMENT_KEY)
    }
}