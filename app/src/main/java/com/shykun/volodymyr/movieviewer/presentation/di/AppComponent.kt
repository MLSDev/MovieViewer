package com.shykun.volodymyr.movieviewer.presentation.di

import com.shykun.volodymyr.movieviewer.presentation.movies.MoviesFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.MoviesPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getMoviesPresenter(): MoviesPresenter
    fun inject(target: MoviesFragment)
}