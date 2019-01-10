package com.shykun.volodymyr.movieviewer.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.presentation.di.AppComponent
import com.shykun.volodymyr.movieviewer.presentation.di.DaggerAppComponent
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListFragment
import com.shykun.volodymyr.movieviewer.presentation.tabs.TabsFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.android.SupportAppNavigator

class AppActivity : AppCompatActivity() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    private val navigator = object : SupportAppNavigator(this, R.id.fragmentContainer) {
        override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return when (screenKey) {
                MOVIE_LIST_FRAGMENT_KEY -> MovieListFragment.newInstance(data as MoviesType)
                else -> throw RuntimeException("Unknown key")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(appToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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
