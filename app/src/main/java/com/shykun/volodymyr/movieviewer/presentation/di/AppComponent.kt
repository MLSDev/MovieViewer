package com.shykun.volodymyr.movieviewer.presentation.di

import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListPresenter
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabPresenter
import com.shykun.volodymyr.movieviewer.presentation.people.PeopleTabPresenter
import com.shykun.volodymyr.movieviewer.presentation.tv.TvTabPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getMoviesPresenter(): MovieTabPresenter
    fun getTvPresenter(): TvTabPresenter
    fun getPeopleTabPresenter(): PeopleTabPresenter
    fun getMovieListPresenter(): MovieListPresenter
    fun inject(target: MovieTabFragment)
    fun inject(target: AppActivity)
}