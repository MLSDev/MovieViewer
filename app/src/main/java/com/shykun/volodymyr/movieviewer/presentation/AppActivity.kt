package com.shykun.volodymyr.movieviewer.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.DiscoverNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.MoviesNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.people.PeopleNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.profile.ProfileFragment
import com.shykun.volodymyr.movieviewer.presentation.profile.ProfileNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.TvNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.utils.NavigationKeys
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.SystemMessage

private const val CURRENT_TAB_KEY = "current_tab_key"

class AppActivity : AppCompatActivity() {

    private val moviesNavigationFragment: MoviesNavigationFragment by lazy {
        supportFragmentManager.findFragmentByTag(NavigationKeys.MOVIES_NAVIGATION_KEY) as? MoviesNavigationFragment
                ?: MoviesNavigationFragment()
    }

    private val tvNavigationFragment: TvNavigationFragment by lazy {
        supportFragmentManager.findFragmentByTag(NavigationKeys.TV_NAVIGATION_KEY) as? TvNavigationFragment
                ?: TvNavigationFragment()
    }

    private val peopleNavigationFragment: PeopleNavigationFragment by lazy {
        supportFragmentManager.findFragmentByTag(NavigationKeys.PEOPLE_NAVIGATION_KEY) as? PeopleNavigationFragment
                ?: PeopleNavigationFragment()
    }

    private val discoverNavigationFragment: DiscoverNavigationFragment by lazy {
        supportFragmentManager.findFragmentByTag(NavigationKeys.DISCOVER_NAVIGATION_KEY) as? DiscoverNavigationFragment
                ?: DiscoverNavigationFragment()
    }

    private val profileNavigationFragment: ProfileNavigationFragment by lazy {
        supportFragmentManager.findFragmentByTag(NavigationKeys.PROFILE_NAVIGATION_KEY) as? ProfileNavigationFragment
                ?: ProfileNavigationFragment()
    }

    private var currentTab = NavigationKeys.MOVIES_NAVIGATION_KEY
    private val cicerone = Cicerone.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupBottomNavigationClickListener()

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.action_movies
        } else {
            savedInstanceState.getString(CURRENT_TAB_KEY)?.let { currentTab = it }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(CURRENT_TAB_KEY, currentTab)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun setupBottomNavigationClickListener() {
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_movies -> cicerone.router.replaceScreen(NavigationKeys.MOVIES_NAVIGATION_KEY)
                R.id.action_tv -> cicerone.router.replaceScreen(NavigationKeys.TV_NAVIGATION_KEY)
                R.id.action_people -> cicerone.router.replaceScreen(NavigationKeys.PEOPLE_NAVIGATION_KEY)
                R.id.action_discover -> cicerone.router.replaceScreen(NavigationKeys.DISCOVER_NAVIGATION_KEY)
                R.id.action_profile -> cicerone.router.replaceScreen(NavigationKeys.PROFILE_NAVIGATION_KEY)
            }
            true
        }
    }

    private val navigator = object : Navigator {

        override fun applyCommands(commands: Array<out Command>) {
            for (command in commands) applyCommand(command)
        }

        private fun applyCommand(command: Command) {
            when (command) {
                is Back -> finish()
                is SystemMessage -> Toast.makeText(this@AppActivity, command.message, Toast.LENGTH_SHORT).show()
                is Replace -> {
                    when (command.screenKey) {
                        NavigationKeys.MOVIES_NAVIGATION_KEY -> changeTab(moviesNavigationFragment)
                        NavigationKeys.TV_NAVIGATION_KEY -> changeTab(tvNavigationFragment)
                        NavigationKeys.PEOPLE_NAVIGATION_KEY -> changeTab(peopleNavigationFragment)
                        NavigationKeys.DISCOVER_NAVIGATION_KEY -> changeTab(discoverNavigationFragment)
                        NavigationKeys.PROFILE_NAVIGATION_KEY -> changeTab(profileNavigationFragment)
                    }
                }
            }
        }

        private fun changeTab(tabFragment: TabNavigationFragment) {
            with(supportFragmentManager.beginTransaction()) {
                supportFragmentManager.fragments.filter { it != tabFragment }.forEach {
                    hide(it)
                    it.userVisibleHint = false
                }
                tabFragment.let {
                    if (it.isAdded) {
                        if (currentTab == it.navigationKey)
                            it.backToRoot()
                        else
                            show(it)
                    }
                    else
                        add(R.id.activityFragmentContainer, it, it.navigationKey)

                    currentTab = it.navigationKey
                    it.userVisibleHint = true
                }
                commit()
            }
        }
    }

    override fun onBackPressed() {
        val currentFragment = (supportFragmentManager.findFragmentByTag(currentTab) as TabNavigationFragment)
        if (!currentFragment.onBackClicked())
            finish()
    }
}
