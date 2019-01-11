package com.shykun.volodymyr.movieviewer.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.data.entity.TvType
import com.shykun.volodymyr.movieviewer.presentation.di.AppComponent
import com.shykun.volodymyr.movieviewer.presentation.di.DaggerAppComponent
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListFragment
import com.shykun.volodymyr.movieviewer.presentation.tabs.TabsFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TV_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import javax.inject.Inject

class AppActivity : AppCompatActivity() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.fragmentContainer) {
        override fun exit() {
            finish()
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return when (screenKey) {
                MOVIE_LIST_FRAGMENT_KEY -> MovieListFragment.newInstance(data as MoviesType)
                TV_LIST_FRAGMENT_KEY -> TvListFragment.newInstance(data as TvType)
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

        setSupportActionBar(appToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        appComponent.inject(this)
        navigatorHolder.setNavigator(navigator)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, TabsFragment())
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manu_app, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
