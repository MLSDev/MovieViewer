package com.shykun.volodymyr.movieviewer.presentation.movies.tab

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shykun.volodymyr.movieviewer.data.entity.Movie
import com.shykun.volodymyr.movieviewer.data.entity.MoviesType
import com.shykun.volodymyr.movieviewer.domain.GetMoviesUseCase
import com.shykun.volodymyr.movieviewer.presentation.movies.details.MOVIE_DETAILS_FRAGMENT_KEY
import com.shykun.volodymyr.movieviewer.presentation.movies.list.MOVIE_LIST_FRAGMENT_KEY
import ru.terrakok.cicerone.Router

class MovieTabViewModel(
        private val getMoviesUseCase: GetMoviesUseCase,
        private val router: Router) : ViewModel() {

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
        getMoviesUseCase.execute(MoviesType.POPULAR, page)
                .doOnSuccess {
                    popularMoviesMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    private fun getTopRatedMovies(page: Int) {
        getMoviesUseCase.execute(MoviesType.TOP_RATED, page)
                .doOnSuccess {
                    topRatedMoviesMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    private fun getUpcomingMovies(page: Int) {
        getMoviesUseCase.execute(MoviesType.UPCOMING, page)
                .doOnSuccess {
                    upcomingMoviesMutableLiveData.value = it
                }
                .doOnError {
                    loadingErrorMutableLiveData.value = it.message
                }
                .subscribe()
    }

    fun onViewAllButtonClicked(moviesType: MoviesType) {
        router.navigateTo(MOVIE_LIST_FRAGMENT_KEY, moviesType)
    }

    fun onMovieClicked(movieId: Int) {
        router.navigateTo(MOVIE_DETAILS_FRAGMENT_KEY, movieId)
    }
}