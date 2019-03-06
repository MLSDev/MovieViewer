package com.shykun.volodymyr.movieviewer.presentation.di

import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.common.RateDialogFragment
import com.shykun.volodymyr.movieviewer.presentation.common.TabNavigationFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.filter.FilterListFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.list.DiscoverListFragment
import com.shykun.volodymyr.movieviewer.presentation.discover.tab.DiscoverFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MovieDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MovieListFragment
import com.shykun.volodymyr.movieviewer.presentation.movies.tab.MovieTabFragment
import com.shykun.volodymyr.movieviewer.presentation.people.details.PersonDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.people.tab.PeopleTabFragment
import com.shykun.volodymyr.movieviewer.presentation.profile.LoginFragment
import com.shykun.volodymyr.movieviewer.presentation.profile.ProfileFragment
import com.shykun.volodymyr.movieviewer.presentation.search.SearchFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.details.TvDetailsFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.list.TvListFragment
import com.shykun.volodymyr.movieviewer.presentation.tv.tab.TvTabFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(target: MovieTabFragment)
    fun inject(target: AppActivity)
    fun inject(target: DiscoverFragment)
    fun inject(target: FilterListFragment)
    fun inject(target: MovieListFragment)
    fun inject(target: TvListFragment)
    fun inject(target: DiscoverListFragment)
    fun inject(target: MovieDetailsFragment)
    fun inject(target: TabNavigationFragment)
    fun inject(target: TvTabFragment)
    fun inject(target: TvDetailsFragment)
    fun inject(target: PeopleTabFragment)
    fun inject(target: PersonDetailsFragment)
    fun inject(target: ProfileFragment)
    fun inject(target: LoginFragment)
    fun inject(target: RateDialogFragment)
    fun inject(target: SearchFragment)
}