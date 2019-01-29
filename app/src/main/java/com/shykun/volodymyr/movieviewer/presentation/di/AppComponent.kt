package com.shykun.volodymyr.movieviewer.presentation.di

import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.base.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.filter.FilterListFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.list.DiscoverListFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.DiscoverFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MovieDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MovieDetailsViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.people.details.PersonDetailsViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PeopleTabViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TvDetailsViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListViewModelFactory
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TvTabViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getMovieListViewModelFactory(): MovieListViewModelFactory
    fun getMovieTabViewModelFactory(): MovieTabViewModelFactory
    fun getPeopleTabViewModelFactory(): PeopleTabViewModelFactory
    fun getTvListViewModelFactory(): TvListViewModelFactory
    fun getTvTabViewModelFactory(): TvTabViewModelFactory
    fun getMovieDetailsViewModelFactory(): MovieDetailsViewModelFactory
    fun getTvDetailsViewModelFactory(): TvDetailsViewModelFactory
    fun getPersonDetailsViewModelFactory(): PersonDetailsViewModelFactory

    fun inject(target: MovieTabFragment)
    fun inject(target: AppActivity)
    fun inject(target: DiscoverFragment)
    fun inject(target: FilterListFragment)
    fun inject(target: MovieListFragment)
    fun inject(target: TvListFragment)
    fun inject(target: DiscoverListFragment)
    fun inject(target: MovieDetailsFragment)
    fun inject(target: TabNavigationFragment)
}