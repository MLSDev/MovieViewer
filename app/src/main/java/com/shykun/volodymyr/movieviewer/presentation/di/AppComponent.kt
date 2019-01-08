package com.shykun.volodymyr.movieviewer.presentation.di

import com.shykun.volodymyr.movieviewer.presentation.movies.MovieTabFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.MovieTabPresenter
import com.shykun.volodymyr.movieviewer.presentation.tv.TvTabPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getMoviesPresenter(): MovieTabPresenter
    fun getTVPresenter(): TvTabPresenter
    fun inject(target: MovieTabFragment)
}