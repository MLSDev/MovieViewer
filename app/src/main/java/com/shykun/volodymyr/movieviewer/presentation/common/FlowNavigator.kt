package com.shykun.volodymyr.movieviewer.presentation.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
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
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MOVIE_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabFragment
import com.shykun.volodymyr.movieviewer.presentation.people.details.PERSON_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.details.PersonDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PEOPLE_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PeopleTabFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TvDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TV_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TvTabFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import java.util.*

class FlowNavigator(private val fragmentManager: FragmentManager, private val containerId: Int) : Navigator {

    private val backStack = Stack<Fragment>()

    override fun applyCommands(commands: Array<out Command>) {
        for (command in commands) applyCommand(command)

    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Back -> closeCurrentFragment()
            is Forward -> {
                when (command.screenKey) {
                    MOVIE_TAB_FRAGMENT_KEY -> openNextFragment(MovieTabFragment())
                    MOVIE_LIST_FRAGMENT_KEY -> openNextFragment(MovieListFragment.newInstance(command.transitionData as MoviesType))
                    MOVIE_DETAILS_FRAGMENT_KEY -> openNextFragment(MovieDetailsFragment.newInstance(command.transitionData as Int))

                    TV_TAB_FRAGMENT_KEY -> openNextFragment(TvTabFragment())
                    TV_LIST_FRAGMENT_KEY -> openNextFragment(TvListFragment.newInstance(command.transitionData as TvType))
                    TV_DETAILS_FRAGMENT_KEY -> openNextFragment(TvDetailsFragment.newInstance(command.transitionData as Int))

                    PEOPLE_TAB_FRAGMENT_KEY -> openNextFragment(PeopleTabFragment())
                    PERSON_DETAILS_FRAGMENT_KEY -> openNextFragment(PersonDetailsFragment.newInstance(command.transitionData as Int))

                    DISCOVER_FRAGMENT_KEY -> openNextFragment(DiscoverFragment())
                    DISCOVER_LIST_FRAGMENT_KEY -> openNextFragment(DiscoverListFragment())
                    FILTER_LIST_FRAGMENT_KEY -> openNextFragment(FilterListFragment.newInstance(command.transitionData as FilterType))
                }
            }
        }
    }

    private fun closeCurrentFragment() {
        with(fragmentManager.beginTransaction()) {
            val currentFragment = backStack.pop()
            fragmentManager.fragments.filter { it == currentFragment }.forEach { remove(it) }
            backStack.peek().let {
                show(it)
                it.userVisibleHint = true
            }
            commit()
        }
    }

    private fun openNextFragment(fragment: Fragment) {
        with(fragmentManager.beginTransaction()) {
            fragment.let {
                add(containerId, it)
                show(it)
            }
            if (!backStack.empty()) {
                backStack.peek().let {
                    hide(it)
                    it.userVisibleHint = false
                }
            }
            backStack.push(fragment)
            commit()
        }
    }
}