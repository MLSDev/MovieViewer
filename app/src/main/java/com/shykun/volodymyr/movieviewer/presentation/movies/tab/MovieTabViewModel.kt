package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.MoviesUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import ru.terrakok.cicerone.Router

class MovieTabViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val popularMoviesMutableLiveData = MutableLiveData<List<Movie>>()
    private val topRatedMoviesMutableLiveData = MutableLiveData<List<Movie>>()
    private val upcomingMoviesMutableLiveData = MutableLiveData<List<Movie>>()
    private val loadingErrorMutableLiveData = MutableLiveData<String>()

    val popularMoviesLiveData: LiveData<List<Movie>> = popularMoviesMutableLiveData
    val topRatedMoviesLiveData: LiveData<List<Movie>> = topRatedMoviesMutableLiveData
    val upcomingMoviesLiveData: LiveData<List<Movie>> = upcomingMoviesMutableLiveData
    val loadingErrorLiveData: LiveData<String> = loadingErrorMutableLiveData


    fun onViewLoaded() {
        getPopularMovies(1)
        getTopRatedMovies(1)
        getUpcomingMovies(1)
    }

    private fun getPopularMovies(page: Int) {
        moviesUseCase.execute(MoviesType.POPULAR, page)
                .subscribe(
                        { response -> popularMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    private fun getTopRatedMovies(page: Int) {
        moviesUseCase.execute(MoviesType.TOP_RATED, page)
                .subscribe(
                        { response -> topRatedMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }

    private fun getUpcomingMovies(page: Int) {
        moviesUseCase.execute(MoviesType.UPCOMING, page)
                .subscribe(
                        { response -> upcomingMoviesMutableLiveData.value = response },
                        { error -> loadingErrorMutableLiveData.value = error.message }
                )
    }
}