package com.shykun.volodymyr.movieviewer.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.di.AppComponent
import com.shykun.volodymyr.movieviewer.presentation.di.DaggerAppComponent
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
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TV_DETAILS_FRAGMENT
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TvDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TV_TAB_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TvTabFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class AppActivity : AppCompatActivity() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var router: Router

    private val navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.fragmentContainer) {
        override fun exit() {
            finish()
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return when (screenKey) {
                MOVIE_LIST_FRAGMENT_KEY -> MovieListFragment.newInstance(data as MoviesType)
                TV_LIST_FRAGMENT_KEY -> TvListFragment.newInstance(data as TvType)
                MOVIE_TAB_FRAGMENT_KEY -> MovieTabFragment()
                TV_TAB_FRAGMENT_KEY -> TvTabFragment()
                PEOPLE_TAB_FRAGMENT_KEY -> PeopleTabFragment()
                MOVIE_DETAILS_FRAGMENT_KEY -> MovieDetailsFragment()
                TV_DETAILS_FRAGMENT -> TvDetailsFragment()
                PERSON_DETAILS_FRAGMENT_KEY -> PersonDetailsFragment()
                else -> throw RuntimeException("Unknown key")
            }
        }

        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@AppActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        appComponent.inject(this)
        navigatorHolder.setNavigator(navigator)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, MovieTabFragment())
                .commit()
        setupBottomNavigationClickListener()
    }

    private fun setupBottomNavigationClickListener() {
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_movies -> router.navigateTo(MOVIE_TAB_FRAGMENT_KEY)
                R.id.action_tv -> router.navigateTo(TV_TAB_FRAGMENT_KEY)
                R.id.action_people -> router.navigateTo(PEOPLE_TAB_FRAGMENT_KEY)
                else -> false
            }
            true
        }
    }
}
