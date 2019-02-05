package com.shykun.volodymyr.movieviewer.presentation.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.shykun.volodymyr.movieviewer.presentation.discover.filter.FILTER_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.filter.FilterListFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.filter.FilterType
import com.shykun.volodymyr.movieviewer.presentation.discover.list.DISCOVER_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.list.DiscoverListFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.DISCOVER_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.DiscoverFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MovieDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.search.MOVIES_SEARCH_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.search.MovieSearchFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MOVIE_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabFragment
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.details.PersonDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.people.search.PEOPLE_SEARCH_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.search.PeopleSearchFragment
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PEOPLE_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PeopleTabFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TvDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.search.TV_SEARCH_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.search.TvSearchFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TV_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TvTabFragment
import ru.terrakok.cicerone.android.SupportAppNavigator

class FlowNavigator(activity: FragmentActivity, fm: FragmentManager, containerId: Int)
    : SupportAppNavigator(activity, fm, containerId) {

    override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
        return null
    }

    override fun createFragment(screenKey: String?, data: Any?): Fragment? {
        return when (screenKey) {
            MOVIE_TAB_FRAGMENT_KEY -> MovieTabFragment()
            MOVIE_LIST_FRAGMENT_KEY -> MovieListFragment.newInstance(data as Bundle)
            MOVIE_DETAILS_FRAGMENT_KEY -> MovieDetailsFragment.newInstance(data as Int)
            MOVIES_SEARCH_FRAGMENT_KEY -> MovieSearchFragment()

            TV_TAB_FRAGMENT_KEY -> TvTabFragment()
            TV_LIST_FRAGMENT_KEY -> TvListFragment.newInstance(data as Bundle)
            TV_DETAILS_FRAGMENT_KEY -> TvDetailsFragment.newInstance(data as Int)
            TV_SEARCH_FRAGMENT_KEY -> TvSearchFragment()

            PEOPLE_TAB_FRAGMENT_KEY -> PeopleTabFragment.newInstance(data as Bundle?)
            PERSON_DETAILS_FRAGMENT_KEY -> PersonDetailsFragment.newInstance(data as Int)
            PEOPLE_SEARCH_FRAGMENT_KEY -> (PeopleSearchFragment())

            DISCOVER_FRAGMENT_KEY -> (DiscoverFragment())
            DISCOVER_LIST_FRAGMENT_KEY -> (DiscoverListFragment())
            FILTER_LIST_FRAGMENT_KEY -> FilterListFragment.newInstance(data as FilterType)

            else -> null
        }
    }
}