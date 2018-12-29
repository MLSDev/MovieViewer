package com.shykun.volodymyr.movieviewer.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.di.AppComponent
import com.shykun.volodymyr.movieviewer.presentation.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_main.*

class AppActivity : AppCompatActivity() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(appToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manu_app, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
