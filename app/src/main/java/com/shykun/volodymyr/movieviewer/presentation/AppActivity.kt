package com.shykun.volodymyr.movieviewer.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuItem
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.di.AppComponent
import com.shykun.volodymyr.movieviewer.presentation.di.DaggerAppComponent
import com.shykun.volodymyr.movieviewer.presentation.movies.MoviesFragment
import com.shykun.volodymyr.movieviewer.presentation.tabs.TabFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.TVFragment
import kotlinx.android.synthetic.main.activity_main.*

class AppActivity : AppCompatActivity() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(appToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, TabFragment())
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
